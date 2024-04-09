package com.bitvolper.yogazzz.presentation.home.reports

import android.app.Activity
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.Reports
import com.bitvolper.yogazzz.presentation.home.account.showFeedbackDialog
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.util.Timer
import kotlin.random.Random

private const val TAG = "ReportsScreen"
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportsScreen(modifier: Modifier = Modifier,
                  paddingValues: PaddingValues = PaddingValues(),
                  onHistory: () -> Unit = { },
                  historyUIState: Resource<History> = Resource.Loading,) {


    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        (context as Activity).showFeedbackDialog()
    }

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

                        TitleCardCompose(history = historyUIState.data)
                        StatisticsCardCompose(history = historyUIState.data)
                        WeightCardCompose()
//                        BmiCardCompose()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleCardCompose(modifier: Modifier = Modifier, history: History = History()) {
    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Row(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.SelfImprovement, contentDescription = null, tint = Color(0xFFf54336))
                Text(text = "${history.data?.size ?: 0}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = stringResource(R.string.yoga), style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = null, tint = Color(0xFF4db05a))
                Text(text = "${history.data?.sumBy { it?.duration?.toInt() ?: 0 }}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = stringResource(R.string.minutes), style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(imageVector = Icons.Rounded.LocalFireDepartment, contentDescription = null, tint = Color(0xFFfe9e26))
                Text(text = "${history.data?.sumBy { it?.kcal?.toInt() ?: 0 }}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(text = stringResource(R.string.kcal), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun StatisticsCardCompose(modifier: Modifier = Modifier, history: History = History()) {

    val modelProducer = remember { CartesianChartModelProducer.build() }

    var isExpanded by remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf("This Week") }

    val today = listOf(LocalDate.now().toString())
    val (datesOfWeek, localDate) = getDateForWeek()
    val (datesOfMonth, localDateMonth) = getDateForMonth()
    val (datesOf6Months, localDate6Months) = getDateFor6Months()

    val bottomAxisValueFormatter = when(selectedPeriod) {
        "Today" -> AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> today[x.toInt() % today.size] }
        "This Week" -> AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> datesOfWeek[x.toInt() % datesOfWeek.size] }
        "This Month" -> AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> datesOfMonth[x.toInt() % datesOfMonth.size] }
        else -> AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> datesOf6Months[x.toInt() % datesOf6Months.size] }
    }

//    LaunchedEffect(Unit) {
//        withContext(Dispatchers.Default) {
//            modelProducer.tryRunTransaction {
//                columnSeries {
//                    repeat(3) {
//
//                        when (selectedPeriod) {
//                            "This Week" -> {
//                                series(
//                                    List(datesOfWeek.size) { date ->
//
//                                        when (it) {
//                                            0 -> if (datesOfWeek[date] == LocalDate.now().dayOfMonth.toString()) history.data?.size?.plus(250) ?: 0 else 0
//                                            1 -> if (datesOfWeek[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.duration?.toIntOrNull() ?: 0 }?.plus(250) ?: 0 else 0
//                                            else -> if (datesOfWeek[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.kcal?.toIntOrNull() ?: 0 } ?: 0 else 0
//                                        }
//                                    },
//                                )
//                            } else -> {
//                                series(
//                                    List(today.size) { date ->
//
//                                        when (it) {
//                                            0 -> history.data?.size?.plus(250) ?: 0
//                                            1 -> history.data?.sumBy { it?.duration?.toIntOrNull() ?: 0 }?.plus(250) ?: 0
//                                            else -> history.data?.sumBy { it?.kcal?.toIntOrNull() ?: 0 } ?: 0
//                                        }
//                                    },
//                                )
//                        }
//                        }
//                    }
//                }
//            }
//            delay(Defaults.TRANSACTION_INTERVAL_MS)
//        }
//    }

    when (selectedPeriod) {
        "Today" -> {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.Default) {
                    modelProducer.tryRunTransaction {
                        columnSeries {
                            repeat(3) {
                                series(
                                    List(today.size) { date ->

                                        when (it) {
                                            0 -> history.data?.size?.plus(250) ?: 0
                                            1 -> history.data?.sumBy { it?.duration?.toIntOrNull() ?: 0 }?.plus(250) ?: 0
                                            else -> history.data?.sumBy { it?.kcal?.toIntOrNull() ?: 0 } ?: 0
                                        }
                                    },
                                )
                            }
                        }
                    }
                    delay(Defaults.TRANSACTION_INTERVAL_MS)
                }
            }
        }
        "This Week" -> {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.Default) {
                    modelProducer.tryRunTransaction {
                        columnSeries {
                            repeat(3) {
                                series(
                                    List(datesOfWeek.size) { date ->

                                        when (it) {
                                            0 -> if (datesOfWeek[date] == LocalDate.now().dayOfMonth.toString()) history.data?.size?.plus(250) ?: 0 else 0
                                            1 -> if (datesOfWeek[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.duration?.toIntOrNull() ?: 0 }?.plus(250) ?: 0 else 0
                                            else -> if (datesOfWeek[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.kcal?.toIntOrNull() ?: 0 } ?: 0 else 0
                                        }
                                    },
                                )
                            }
                        }
                    }
                    delay(Defaults.TRANSACTION_INTERVAL_MS)
                }
            }
        }
        "This Month" -> {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.Default) {
                    modelProducer.tryRunTransaction {
                        columnSeries {
                            repeat(3) {
                                series(
                                    List(datesOfMonth.size) { date ->

                                        when (it) {
                                            0 -> if (datesOfMonth[date] == LocalDate.now().dayOfMonth.toString()) history.data?.size?.plus(250) ?: 0 else 0
                                            1 -> if (datesOfMonth[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.duration?.toIntOrNull() ?: 0 }?.plus(250) ?: 0 else 0
                                            else -> if (datesOfMonth[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.kcal?.toIntOrNull() ?: 0 } ?: 0 else 0
                                        }
                                    },
                                )
                            }
                        }
                    }
                    delay(Defaults.TRANSACTION_INTERVAL_MS)
                }
            }
        }
        else -> {
            LaunchedEffect(Unit) {
                withContext(Dispatchers.Default) {
                    modelProducer.tryRunTransaction {
                        columnSeries {
                            repeat(3) {
                                series(
                                    List(datesOf6Months.size) { date ->

                                        when (it) {
                                            0 -> if (datesOf6Months[date] == LocalDate.now().dayOfMonth.toString()) history.data?.size?.plus(250) ?: 0 else 0
                                            1 -> if (datesOf6Months[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.duration?.toIntOrNull() ?: 0 }?.plus(250) ?: 0 else 0
                                            else -> if (datesOf6Months[date] == LocalDate.now().dayOfMonth.toString()) history.data?.sumBy { it?.kcal?.toIntOrNull() ?: 0 } ?: 0 else 0
                                        }
                                    },
                                )
                            }
                        }
                    }
                    delay(Defaults.TRANSACTION_INTERVAL_MS)
                }
            }
        }
    }

    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.statistics),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.weight(1f))

                ExposedBottomMenu(
                    isExpanded = isExpanded,
                    onExpand = { isExpanded = it },
                    selectedPeriod = selectedPeriod,
                    onSelectPeriod = { selectedPeriod = it }
                )
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

                    Text(text = stringResource(id = R.string.yoga), style = MaterialTheme.typography.bodyMedium)
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null, tint = Color(0xFF4db05a))
                    Text(text = stringResource(id = R.string.minutes), style = MaterialTheme.typography.bodyMedium)
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Rounded.Rectangle, contentDescription = null, tint = Color(0xFFfe9e26))

                    Text(text = stringResource(id = R.string.kcal), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun WeightCardCompose(modifier: Modifier = Modifier) {

    var isExpanded by remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf("Last 6 Months") }

    val (datesOfWeek, localDate) = getDayForWeek()
    val (dayOfMonth, localDateMonth) = getDayForMonth()
    val (dayOf6Months, localDate6Months) = getLast6Months()


    val color = MaterialTheme.colorScheme.primary
    val marker = rememberMarker().apply { labelFormatter = MarkerLabelFormatter { markedEntries, chartValues ->
        markedEntries.map { it.entry.y }.joinToString().plus(" kg")
    } }

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) { modelProducer.tryRunTransaction { lineSeries { series(78, 66, 75, 78, 70, 78) } } }


    val bottomAxisValueFormatter = when (selectedPeriod) {
        "This Week" -> AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> datesOfWeek[x.toInt() % datesOfWeek.size] }
        else -> AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _, _ -> dayOf6Months[x.toInt() % dayOf6Months.size] }
    }

    Card(border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.weight),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = modifier.weight(1f))

                ExposedBottomMenuWeight(
                    isExpanded = isExpanded,
                    onExpand = { isExpanded = it },
                    selectedPeriod = selectedPeriod,
                    onSelectPeriod = { selectedPeriod = it }
                )
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
                zoomState = rememberVicoZoomState(zoomEnabled = true),
                marker = marker
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BmiCardCompose(modifier: Modifier = Modifier) {

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

            Divider(color = MaterialTheme.colorScheme.outline)

            Spacer(modifier = modifier.requiredHeight(8.dp))

            GaugeChart(modifier = modifier
                .fillMaxWidth()
                .requiredHeight(150.dp), percentValue = 10, primaryColor = Color.Red)
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