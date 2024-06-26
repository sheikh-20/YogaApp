package com.bitvolper.yogazzz.presentation.home.history

import android.content.res.Configuration
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import java.util.Date

private const val TAG = "HistoryScreen"
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onHistory: () -> Unit = { },
    historyUIState: Resource<History> = Resource.Loading,
) {

    val dayTheme = if (isSystemInDarkTheme()) R.style.CustomDayDark else R.style.CustomDay
    val weekTheme = if (isSystemInDarkTheme()) R.style.CustomWeekDark else R.style.CustomWeek
    val customTheme = if (isSystemInDarkTheme()) R.style.CustomCalendarDark else R.style.CustomCalendar

    var selectedDate by remember { mutableStateOf("") }
    var selectedDay by remember {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = !isRefreshing

                onHistory()

                delay(1_000L)
                isRefreshing = !isRefreshing
            }
        })

    LaunchedEffect(key1 = Unit) {
        onHistory()
    }

    Box(modifier = modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState), contentAlignment = Alignment.Center) {

        Column(modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
        ) {

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
                                    CalendarView(ContextThemeWrapper(it, customTheme)).apply {

                                        val date: Date = Date(date)
                                        selectedDate = android.text.format.DateFormat.format("d-M-yyyy", date) as String
                                        selectedDay =  android.text.format.DateFormat.format("EEE, MMM dd", date) as String
                                    }
                                },
                                update = {
                                    it.apply {
                                        dateTextAppearance = dayTheme
                                        weekDayTextAppearance = weekTheme

                                        setOnDateChangeListener { view, year, month, dayOfMonth ->
                                            selectedDate = "$dayOfMonth-${month.inc()}-$year"
                                            Timber.tag(TAG).d(selectedDate)

                                            val calendar: Calendar = Calendar.getInstance()
                                            calendar.set(year, month, dayOfMonth)
                                            selectedDay =  android.text.format.DateFormat.format("EEE, MMM dd", calendar.time) as String
                                        }
                                    }
                                })
                        }

                        HistoryCompose(history = historyUIState.data, selectedDate = selectedDate, selectedDay = selectedDay)
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HistoryCompose(modifier: Modifier = Modifier, history: History = History(), selectedDate: String = "5-5-2024", selectedDay: String = "Mon") {

    val filter = history.data?.filter {
        selectedDate == it?.date
    }

    Card(border = BorderStroke(width = .5.dp, color = MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                Text(text = selectedDay,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)

                Spacer(modifier = modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null, tint = Color(0xFFf54336), modifier = modifier.size(20.dp))
                        Text(text = "${filter?.size ?: 0}", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null, tint = Color(0xFF4db05a), modifier = modifier.size(20.dp))
                        Text(text = "${filter?.sumBy { it?.duration?.toIntOrNull() ?: 0 }} mins", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null, tint = Color(0xFFfe9e26), modifier = modifier.size(20.dp))
                        Text(text = "${filter?.sumBy { it?.kcal?.toIntOrNull() ?: 0 }} kcal", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Divider()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                if (filter?.isEmpty() == true) {

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Image(painter = painterResource(id = R.drawable.ic_empty),
                            contentDescription = null,
                            modifier = modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.CenterHorizontally)
                                .size(150.dp))

                        Text(text = stringResource(id = R.string.empty),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold)

                        Text(
                            text = stringResource(R.string.you_did_not_exercise_on_this_date),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    filter?.forEach {
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
private fun HistoryCard(
    modifier: Modifier = Modifier,
    history: History.Data = History.Data(),
) {

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


            if (history.type != "yoga") {
                IconButton(onClick = { /*TODO*/ },
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                        .background(color = Color(0xFFE5E5E5), shape = RoundedCornerShape(50))) {
                    Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = null, modifier = modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
                }
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

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null, tint = Color(0xFF4db05a), modifier = modifier.size(20.dp))
                    Text(text = "${history.duration ?: 0} mins", style = MaterialTheme.typography.bodyMedium)
                }

                if (history.type == "yoga") {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null, tint = Color(0xFFfe9e26), modifier = modifier.size(20.dp))
                        Text(text = "${history.kcal ?: 0} kcal", style = MaterialTheme.typography.bodyMedium)
                    }
                }
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