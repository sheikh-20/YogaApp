package com.bitvolper.yogazzz.presentation.home.reports

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Rectangle
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.Reports
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportsScreen(modifier: Modifier = Modifier,
                  paddingValues: PaddingValues = PaddingValues(),
                  onReports: () -> Unit = { },
                  reportsUIState: Resource<Reports> = Resource.Loading,) {


    val coroutineScope = rememberCoroutineScope()

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = !isRefreshing

                onReports()

                delay(1_000L)
                isRefreshing = !isRefreshing
            }
        })


    LaunchedEffect(key1 = Unit) {
        onReports()
    }

    Box(modifier = modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState), contentAlignment = Alignment.Center) {


        Column(modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
        ) {

            when (reportsUIState) {
                is Resource.Loading -> {
                    CircularProgressIndicator()
                }

                is Resource.Failure -> {
                    Text(text = reportsUIState.throwable.toString())
                }

                is Resource.Success -> {
                    Column(modifier = modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(), start = 16.dp, end = 16.dp
                        )
                        .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)) {

                        TitleCardCompose(reports = reportsUIState.data)
                        StatisticsCardCompose()
                        WeightCardCompose()
                        BmiCardCompose()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleCardCompose(modifier: Modifier = Modifier, reports: Reports = Reports()) {
    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null)
                Text(text = "${reports.data?.size ?: 0}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = "yoga", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null)
                Text(text = "${reports.data?.sumBy { it?.duration?.toInt() ?: 0 }}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = "minutes", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null)
                Text(text = "${reports.data?.sumBy { it?.kcal?.toInt() ?: 0 }}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(text = "kcal", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

fun getRandomEntries() = List(4) { entryOf(it, Random.nextFloat() * 16f) }

@Preview(showBackground = true)
@Composable
private fun StatisticsCardCompose(modifier: Modifier = Modifier) {

    val chartEntryModelProducer = ChartEntryModelProducer(getRandomEntries())


    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Statistics",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.weight(1f))

                OutlinedButton(onClick = { /*TODO*/ }, border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                    Row {
                        Text(text = "This Week", style = MaterialTheme.typography.titleMedium)
                        Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                    }
                }
            }

            Divider()

            Chart(
                chart = columnChart(),
                chartModelProducer = chartEntryModelProducer,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            )

            Divider()

            Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null)

                    Text(text = "Yoga", style = MaterialTheme.typography.bodyMedium)
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null)
                    Text(text = "Minutes", style = MaterialTheme.typography.bodyMedium)
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null)

                    Text(text = "Kcal", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeightCardCompose(modifier: Modifier = Modifier) {

    val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f)

    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Weight",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.weight(1f))

                OutlinedButton(onClick = { /*TODO*/ }, border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
                    Row {
                        Text(text = "Last 6 Months", style = MaterialTheme.typography.titleMedium)
                        Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                    }
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
                }
            }

            Divider()

            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BmiCardCompose(modifier: Modifier = Modifier) {

    val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f)

    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "BMI (kg/m2): 22.2",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.weight(1f))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
                }
            }

            Divider()

            Chart(
                chart = lineChart(),
                model = chartEntryModel,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis(),
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ReportsLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        ReportsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ReportsDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        ReportsScreen()
    }
}