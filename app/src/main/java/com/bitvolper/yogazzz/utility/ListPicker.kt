package com.bitvolper.yogazzz.utility

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <E> ListPicker(
    initialValue: E,
    list: ImmutableWrapper<List<E>>,
    modifier: Modifier = Modifier,
    wrapSelectorWheel: Boolean = false,
    format: E.() -> String = { this.toString() },
    onValueChange: (E) -> Unit,
    beyondBoundsPageCount: Int = 1,
    textStyle: TextStyle = LocalTextStyle.current,
    verticalPadding: Dp = 16.dp,
    dividerColor: Color = MaterialTheme.colorScheme.outline,
    dividerThickness: Dp = 1.dp
) {
    val listSize = list.value.size
    val coercedBeyondBoundsPageCount = beyondBoundsPageCount.coerceIn(0..listSize / 2)
    val indexOfInitialValue = remember(list) {
        list.value.indexOf(initialValue).takeIf { it != -1 } ?: 0
    }
    val visibleItemsCount = 1 + coercedBeyondBoundsPageCount * 2

    val iteration =
        if (wrapSelectorWheel)
            remember(coercedBeyondBoundsPageCount, listSize) {
                (Int.MAX_VALUE - 2 * coercedBeyondBoundsPageCount) / listSize
            }
        else 1

    val intervals = remember(coercedBeyondBoundsPageCount, iteration, listSize) {
        listOf(
            0,
            coercedBeyondBoundsPageCount,
            coercedBeyondBoundsPageCount + iteration * listSize,
            coercedBeyondBoundsPageCount + iteration * listSize + coercedBeyondBoundsPageCount,
        )
    }

    val initialFirstVisibleItemIndex = remember(indexOfInitialValue, listSize, iteration) {
        indexOfInitialValue + (listSize * (iteration / 2))
    }

    val state = rememberLazyListState(initialFirstVisibleItemIndex = initialFirstVisibleItemIndex)
    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .collectLatest {
                onValueChange(list.value[it % listSize])
            }
    }

    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val onSizeChanged = { intSize: IntSize ->
        itemHeight = density.toDp(intSize.height)
    }
    Box(modifier = modifier) {
        LazyColumn(
            state = state,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight * visibleItemsCount)
                .fadingEdge(
                    brush = remember {
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.5f to Color.Black,
                            1f to Color.Transparent
                        )
                    },
                )
        ) {
            items(
                count = intervals.last(),
                key = { it },
            ) { index ->
                when (index) {
                    in intervals[0]..<intervals[1] -> Text(
                        text = if (wrapSelectorWheel) list.value[(index - coercedBeyondBoundsPageCount + listSize) % listSize].format() else "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        modifier = Modifier
                            .onSizeChanged(onSizeChanged)
                            .padding(vertical = verticalPadding)
                    )

                    in intervals[1]..<intervals[2] -> Text(
                        text = list.value[(index - coercedBeyondBoundsPageCount) % listSize].format(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        modifier = Modifier
                            .onSizeChanged(onSizeChanged)
                            .padding(vertical = verticalPadding)
                    )

                    in intervals[2]..<intervals[3] -> Text(
                        text = if (wrapSelectorWheel) list.value[(index - coercedBeyondBoundsPageCount) % listSize].format() else "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        modifier = Modifier
                            .onSizeChanged(onSizeChanged)
                            .padding(vertical = verticalPadding),
                    )
                }
            }
        }

        Divider(
            modifier = Modifier.offset(y = itemHeight * coercedBeyondBoundsPageCount - dividerThickness / 2),
            thickness = dividerThickness,
            color = dividerColor,
        )

        Divider(
            modifier = Modifier.offset(y = itemHeight * (coercedBeyondBoundsPageCount + 1) - dividerThickness / 2),
            thickness = dividerThickness,
            color = dividerColor,
        )
    }
}

@Preview(widthDp = 100)
@Composable
fun PreviewListPicker1() {
    YogaAppTheme {
        Surface {
            var value by remember { mutableStateOf("5") }
            val values = remember { (1..10).map { it.toString() } }
            ListPicker(
                initialValue = value,
                list = values.toImmutableWrapper(),
                modifier = Modifier,
                onValueChange = {
                    value = it
                },
                textStyle = TextStyle(fontSize = 32.sp),
                verticalPadding = 8.dp,
            )
        }
    }
}