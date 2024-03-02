package com.bitvolper.yogazzz.presentation.home.history

import android.content.res.Configuration
import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import timber.log.Timber

private const val TAG = "HistoryScreen"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier,
                  paddingValues: PaddingValues = PaddingValues(),
                  onHistory: () -> Unit = {  },
                  historyUIState: Resource<History> = Resource.Loading,
                  ) {

    LaunchedEffect(key1 = Unit) {
        onHistory()
    }

    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {

        when (historyUIState) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }

            is Resource.Failure -> {
                Text(text = historyUIState.throwable.toString())
            }

            is Resource.Success -> {

                Timber.tag(TAG).d(historyUIState.data.data.toString())
                
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    Card(border = BorderStroke(width = .5.dp, color = MaterialTheme.colorScheme.outlineVariant), colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                        AndroidView(
                            factory = {
                                CalendarView(it).apply {

                                }
                            })
                    }

                    HistoryCompose(history = historyUIState.data)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HistoryCompose(modifier: Modifier = Modifier, history: History = History()) {
    Card(border = BorderStroke(width = .5.dp, color = MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                Text(text = "Tue Dec 5",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)

                Spacer(modifier = modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null)
                        Text(text = "0", style = MaterialTheme.typography.bodyLarge)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null)
                        Text(text = "0 mins", style = MaterialTheme.typography.bodyLarge)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null)
                        Text(text = "0 kcal", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            Divider()


            if (history.data?.isEmpty() == true) {
                Image(painter = painterResource(id = R.drawable.ic_empty),
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .size(150.dp))

                Text(text = "Empty",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold)


                Text(
                    text = "You did not exercise on this date",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    history.data?.forEach {
                        HistoryCard(history = it ?: return@forEach)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun HistoryCard(modifier: Modifier = Modifier,
                        history: History.Data = History.Data(), ) {

    val context = LocalContext.current


    Row(modifier = modifier
        .fillMaxWidth()
        .clickable(
            onClick = { },
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        Box(modifier = modifier.requiredSize(height = 100.dp, width = 100.dp)) {

            Card(onClick = { }, modifier = modifier.padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant)) {

                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(history.image)
                        .crossfade(true)
                        .build(),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                    contentDescription = null,
                    modifier = modifier
                        .size(height = 100.dp, width = 100.dp),
                    contentScale = ContentScale.Crop)
            }

            IconButton(onClick = { /*TODO*/ },
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)
                    .background(color = Color(0xFFE5E5E5), shape = RoundedCornerShape(50))) {
                Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = null, modifier = modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
            }

        }

        Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = history.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = history.duration ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = ".", style = MaterialTheme.typography.bodySmall)
                Text(text = history.category ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HistoryLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        HistoryScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HistoryDarkThemePreview() {
   YogaAppTheme(darkTheme = true) {
       HistoryScreen()
   }
}