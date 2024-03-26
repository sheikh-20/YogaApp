package com.bitvolper.yogazzz.presentation.accountsetup.utility


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bitvolper.yogazzz.utility.ImmutableWrapper
import com.bitvolper.yogazzz.utility.fadingEdge
import com.bitvolper.yogazzz.utility.toDp
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
    outOfBoundsPageCount: Int = 1,
    textStyle: TextStyle = LocalTextStyle.current,
    verticalPadding: Dp = 16.dp,
    dividerThickness: Dp = 2.dp
) {
    val listSize = list.value.size
    val coercedOutOfBoundsPageCount = outOfBoundsPageCount.coerceIn(0..listSize / 2)
    val indexOfInitialValue = remember(key1 = list) {
        list.value.indexOf(initialValue).takeIf { it != -1 } ?: 0
    }
    val visibleItemsCount = 1 + coercedOutOfBoundsPageCount * 2

    val iteration =
        if (wrapSelectorWheel)
            remember(key1 = coercedOutOfBoundsPageCount, key2 = listSize) {
                (Int.MAX_VALUE - 2 * coercedOutOfBoundsPageCount) / listSize
            }
        else 1

    val intervals =
        remember(key1 = coercedOutOfBoundsPageCount, key2 = iteration, key3 = listSize) {
            listOf(
                0,
                coercedOutOfBoundsPageCount,
                coercedOutOfBoundsPageCount + iteration * listSize,
                coercedOutOfBoundsPageCount + iteration * listSize + coercedOutOfBoundsPageCount,
            )
        }

    val initialFirstVisibleItemIndex = remember(
        key1 = indexOfInitialValue,
        key2 = listSize,
        key3 = iteration,
    ) {
        indexOfInitialValue + (listSize * (iteration / 2))
    }

    val lazyListState =
        rememberLazyListState(initialFirstVisibleItemIndex = initialFirstVisibleItemIndex)
    LaunchedEffect(key1 = Unit) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collectLatest {
                onValueChange(list.value[it % listSize])
            }
    }

    val density = LocalDensity.current
    val itemHeight = run {
        val textMeasurer = rememberTextMeasurer()
        remember(textStyle, textMeasurer, verticalPadding, density) {
            density.toDp(
                textMeasurer.measure(
                    text = "",
                    style = textStyle,
                ).size.height
            ) + verticalPadding * 2
        }
    }
    Box(modifier = modifier) {
        LazyColumn(
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight * visibleItemsCount)
                .fadingEdge(
                    brush = remember {
                        Brush.verticalGradient(
                            0F to Color.Transparent,
                            0.5F to Color.Black,
                            1F to Color.Transparent
                        )
                    },
                ),
        ) {
            items(
                count = intervals.last(),
                key = { it },
            ) { index ->
                when (index) {
                    in intervals[0]..<intervals[1] -> Text(
                        text = if (wrapSelectorWheel) list.value[(index - coercedOutOfBoundsPageCount + listSize) % listSize].format() else "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        modifier = Modifier.padding(vertical = verticalPadding),
                        fontWeight = FontWeight.Bold
                    )

                    in intervals[1]..<intervals[2] ->
                            Text(
                                text = list.value[(index - coercedOutOfBoundsPageCount) % listSize].format(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = textStyle ,
                                modifier = Modifier.padding(vertical = verticalPadding),
                                color = if (list.value[(index - coercedOutOfBoundsPageCount) % listSize].format() == initialValue) MaterialTheme.colorScheme.primary else Color.Unspecified,
                                fontWeight = if (list.value[(index - coercedOutOfBoundsPageCount) % listSize].format() == initialValue) FontWeight.Bold else FontWeight.Normal,
                            )


                    in intervals[2]..<intervals[3] -> Text(
                        text = if (wrapSelectorWheel) list.value[(index - coercedOutOfBoundsPageCount) % listSize].format() else "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        modifier = Modifier.padding(vertical = verticalPadding),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Divider(
            modifier = Modifier.offset(y = itemHeight * coercedOutOfBoundsPageCount - dividerThickness / 2),
            thickness = dividerThickness,
            color = MaterialTheme.colorScheme.primary,
        )

        Divider(
            modifier = Modifier.offset(y = itemHeight * (coercedOutOfBoundsPageCount + 1) - dividerThickness / 2),
            thickness = dividerThickness,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}