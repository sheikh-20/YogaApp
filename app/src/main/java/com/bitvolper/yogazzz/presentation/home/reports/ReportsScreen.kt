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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.Reports
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.Resource
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberTopAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberEndAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.chart.zoom.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.component.shape.roundedCornerShape
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerLabelFormatter
import com.patrykandpatrick.vico.core.marker.MarkerVisibilityChangeListener
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.columnSeries
import com.patrykandpatrick.vico.core.model.lineSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Timer
import kotlin.random.Random

private const val TAG = "ReportsScreen"
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
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
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
                Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null, tint = Color(0xFFf54336))
                Text(text = "${reports.data?.size ?: 0}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = "yoga", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null, tint = Color(0xFF4db05a))
                Text(text = "${reports.data?.sumBy { it?.duration?.toInt() ?: 0 }}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = "minutes", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null, tint = Color(0xFFfe9e26))
                Text(text = "${reports.data?.sumBy { it?.kcal?.toInt() ?: 0 }}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = "kcal", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun StatisticsCardCompose(modifier: Modifier = Modifier) {

    val modelProducer = remember { CartesianChartModelProducer.build() }


    val datesOfWeek = listOf("16", "17", "18", "19", "20", "21", "22")
    val bottomAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> datesOfWeek[x.toInt() % datesOfWeek.size] }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            modelProducer.tryRunTransaction {
                columnSeries {
                    repeat(3) {
                        series(
                            List(Defaults.ENTRY_COUNT) {
                                Defaults.COLUMN_LAYER_MIN_Y +
                                        Random.nextFloat() * Defaults.COLUMN_LAYER_RELATIVE_MAX_Y
                            },
                        )
                    }
                }
                lineSeries { series(List(Defaults.ENTRY_COUNT) { Random.nextFloat() * Defaults.MAX_Y }) }
            }
            delay(Defaults.TRANSACTION_INTERVAL_MS)
        }
    }

    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Statistics",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.weight(1f))

                Chip(onClick = { /*TODO*/ },
                    modifier = modifier.then(modifier.requiredHeight(35.dp)),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
                    colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent)) {
                    Row {
                        Text(text = "This Week", style = MaterialTheme.typography.titleMedium)
                        Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                    }
                }
            }

            Divider(color = MaterialTheme.colorScheme.outline)

            CartesianChartHost(
                chart =
                rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        columns =
                        columnColors.map {
                            rememberLineComponent(color = it, thickness = 8.dp, shape = Shapes.roundedCornerShape(10.dp))
                        },
                    ),
                    bottomAxis = rememberBottomAxis(
                        valueFormatter = bottomAxisValueFormatter
                    ),
                ),
                modelProducer = modelProducer,
                modifier = modifier,
                marker = rememberMarker(),
                runInitialAnimation = false,
                zoomState = rememberVicoZoomState(zoomEnabled = false),
//                horizontalLayout = com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout.fullWidth()
            )

            Divider(color = MaterialTheme.colorScheme.outline)

            Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null, tint = Color(0xFFf54336))

                    Text(text = "Yoga", style = MaterialTheme.typography.bodyMedium)
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null, tint = Color(0xFF4db05a))
                    Text(text = "Minutes", style = MaterialTheme.typography.bodyMedium)
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null, tint = Color(0xFFfe9e26))

                    Text(text = "Kcal", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun WeightCardCompose(modifier: Modifier = Modifier) {

    val color = MaterialTheme.colorScheme.primary
    val marker = rememberMarker().apply { labelFormatter = MarkerLabelFormatter { markedEntries, chartValues ->
        markedEntries.map { it.entry.y }.joinToString().plus(" kg")
    } }

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) { modelProducer.tryRunTransaction { lineSeries { series(78, 66, 75, 78, 70, 78) } } }

    val daysOfWeek = listOf("Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val bottomAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> daysOfWeek[x.toInt() % daysOfWeek.size] }

    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Weight",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.weight(1f))

                Chip(onClick = { /*TODO*/ },
                    modifier = modifier.then(modifier.requiredHeight(35.dp)),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
                    colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent)) {
                    Row {
                        Text(text = "Last 6 Months", style = MaterialTheme.typography.titleMedium)
                        Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                    }
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
                }
            }

            Divider(color = MaterialTheme.colorScheme.outline)

//            Chart(
//                chart = lineChart(),
//                model = chartEntryModel,
//                startAxis = rememberStartAxis(),
//                bottomAxis = rememberBottomAxis(),
//                horizontalLayout = com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout.fullWidth()
//            )

            CartesianChartHost(
                rememberCartesianChart(
                    rememberLineCartesianLayer(
                        lines = listOf(rememberLineSpec(shader = DynamicShaders.color(color), thickness = 4.dp)),
                        axisValueOverrider = AxisValueOverrider.fixed(minY = 55f, maxY = 80f)
                    ),

                    startAxis = rememberStartAxis(itemPlacer = AxisItemPlacer.Vertical.step(step = { 5f }, false), guideline = null),
                    bottomAxis = rememberBottomAxis(
                        valueFormatter = bottomAxisValueFormatter,
                        itemPlacer = AxisItemPlacer.Horizontal.default(addExtremeLabelPadding = true),
                        guideline = null,
                        ),
                    persistentMarkers = mapOf(PERSISTENT_MARKER_X to marker),
                ),
                modelProducer,
                horizontalLayout = com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout.fullWidth(),
                zoomState = rememberVicoZoomState(zoomEnabled = false),
                marker = marker
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BmiCardCompose(modifier: Modifier = Modifier) {

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) { modelProducer.tryRunTransaction { lineSeries { series(4, 12, 8, 16) } } }

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

            CartesianChartHost(
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(),
                ),
                modelProducer,
            )
        }
    }
}


private const val PERSISTENT_MARKER_X = 3f


private val columnColors = listOf(Color(0xFFf54336), Color(0xFF4db05a), Color(0xFFfe9e26))


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