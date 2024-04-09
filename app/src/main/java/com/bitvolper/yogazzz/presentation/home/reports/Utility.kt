package com.bitvolper.yogazzz.presentation.home.reports

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.model.CartesianLayerModel
import com.patrykandpatrick.vico.core.model.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.model.LineCartesianLayerModel
import kotlinx.coroutines.stream.consumeAsFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale
import java.util.stream.Collector
import java.util.stream.Collectors


object Defaults {
    const val TRANSACTION_INTERVAL_MS = 2000L
    const val MULTI_SERIES_COUNT = 3
    const val ENTRY_COUNT = 7
    const val MAX_Y = 20
    const val COLUMN_LAYER_MIN_Y = 2
    const val COLUMN_LAYER_RELATIVE_MAX_Y = MAX_Y - COLUMN_LAYER_MIN_Y
}


val CartesianLayerModel.Entry.y
    get() =
        when (this) {
            is ColumnCartesianLayerModel.Entry -> y
            is LineCartesianLayerModel.Entry -> y
            else -> throw IllegalArgumentException("Unexpected `CartesianLayerModel.Entry` implementation.")
        }


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExposedBottomMenu(modifier: Modifier = Modifier,
                      isExpanded: Boolean = false,
                      onExpand: (Boolean) -> Unit = { },
                      selectedPeriod: String = "",
                      onSelectPeriod: (String) -> Unit = {  }) {

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpand) {

        Chip(onClick = { onExpand(true) },
            modifier = modifier
                .then(modifier.requiredHeight(35.dp))
                .menuAnchor(),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
            colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent)) {
            Row {
                Text(text = selectedPeriod, style = MaterialTheme.typography.titleMedium)

                Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
            }
        }

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { onExpand(false) }) {
            DropdownMenuItem(
                text = { Text(text = "Today") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("Today")
                })

            DropdownMenuItem(
                text = { Text(text = "This Week") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("This Week")
                })

            DropdownMenuItem(
                text = { Text(text = "This Month") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("This Month")
                })

            DropdownMenuItem(
                text = { Text(text = "Last 6 Months") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("Last 6 Months")
                })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExposedBottomMenuWeight(modifier: Modifier = Modifier,
                      isExpanded: Boolean = false,
                      onExpand: (Boolean) -> Unit = { },
                      selectedPeriod: String = "",
                      onSelectPeriod: (String) -> Unit = {  }) {

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpand) {

        Chip(onClick = { onExpand(true) },
            modifier = modifier
                .then(modifier.requiredHeight(35.dp))
                .menuAnchor(),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
            colors = ChipDefaults.chipColors(backgroundColor = Color.Transparent)) {
            Row {
                Text(text = selectedPeriod, style = MaterialTheme.typography.titleMedium)

                Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
            }
        }

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { onExpand(false) }) {

            DropdownMenuItem(
                text = { Text(text = "This Week") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("This Week")
                })

            DropdownMenuItem(
                text = { Text(text = "Last 6 Months") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("Last 6 Months")
                })
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ExposedRefreshBottomMenu(modifier: Modifier = Modifier,
                      isExpanded: Boolean = false,
                      onExpand: (Boolean) -> Unit = { },
                      onRefresh: () -> Unit = { }) {

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpand) {

        IconButton(onClick = { onExpand(true) },
            modifier = modifier
                .then(modifier.requiredHeight(30.dp))
                .menuAnchor()) {

            Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
        }

        ExposedDropdownMenu(modifier = modifier.then(modifier.requiredWidth(120.dp)), expanded = isExpanded, onDismissRequest = { onExpand(false) }) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.refresh), style = MaterialTheme.typography.bodyLarge) },
                onClick = {
                    onExpand(false)
                    onRefresh()
                })
        }
    }
}


fun getDateForWeek(): Pair<List<String>, List<LocalDate>> {
    val today = LocalDate.now()
    val startOfWeek = today.minusDays(6) // Subtract 6 days to get the start of the 7-day range

    val datesInRange = mutableListOf<LocalDate>()
    var currentDate = startOfWeek
    while (currentDate.isBefore(today) || currentDate == today) {
        datesInRange.add(currentDate)
        currentDate = currentDate.plusDays(1)
    }

    return Pair(first = datesInRange.map { it.dayOfMonth.toString() }, second = datesInRange.map { it })
}

fun getDayForWeek(): Pair<List<String>, List<LocalDate>> {
    // Get today's date
    val today = LocalDate.now()

    // Create a list to store the formatted dates
    val formattedLast7Days = mutableListOf<String>()

    // Define the formatter for day of the week
    val formatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH)

    // Iterate backwards for the last 7 days or until today
    var dateIterator = today
    repeat(7) {
        val formattedDate = dateIterator.format(formatter)
        formattedLast7Days.add(formattedDate)
        dateIterator = dateIterator.minusDays(1)
    }

    // Reverse the list to maintain chronological order
    formattedLast7Days.reverse()
    return Pair(first = formattedLast7Days, second = listOf())
}

fun getDateForMonth(): Pair<List<String>, List<LocalDate>> {
    // Get the current date
    val currentDate = LocalDate.now()

    // Get the first day of the current month
    val firstDayOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth())

    // Get the last day of the current month
    val lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth())

    // Generate a list of dates for the current month
    val datesList = mutableListOf<LocalDate>()
    var dateIterator = firstDayOfMonth
    while (dateIterator.isBefore(lastDayOfMonth) || dateIterator.isEqual(lastDayOfMonth)) {
        datesList.add(dateIterator)
        dateIterator = dateIterator.plusDays(1)
    }


    return Pair(first = datesList.map { it.dayOfMonth.toString() }, second = datesList.map { it })
}

fun getDayForMonth(): Pair<List<String>, List<LocalDate>> {
    // Get today's date
    val today = LocalDate.now()

    // Get the first day of the current month
    val firstDayOfMonth = today.withDayOfMonth(1)

    // Get the last day of the current month
    val lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth())

    // Create a list to store the days of the current month
    val daysOfMonth = mutableListOf<LocalDate>()

    // Iterate through each day of the current month and add it to the list
    var currentDate = firstDayOfMonth
    while (currentDate.isBefore(lastDayOfMonth) || currentDate == lastDayOfMonth) {
        daysOfMonth.add(currentDate)
        currentDate = currentDate.plusDays(1)
    }

    // Print the list of days of the current month
    val formatter = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)
    println("List of days for the current month:")

    return Pair(first = daysOfMonth.map { it.format(formatter) }, second = listOf())

}


fun getDateFor6Months(): Pair<List<String>, List<LocalDate>> {
    // Get the current year and month
    val currentYearMonth = YearMonth.now()

    // Create a list to store the dates
    val datesList = mutableListOf<LocalDate>()

    // Iterate over the past 6 months
    for (i in 0 until 6) {
        // Calculate the year and month for the current iteration
        val yearMonth = currentYearMonth.minusMonths(i.toLong())

        // Get the first day of the month
        val firstDayOfMonth = yearMonth.atDay(1)

        // Get the last day of the month
        val lastDayOfMonth = yearMonth.atEndOfMonth()

        // Add dates for the current month to the list
        var dateIterator = firstDayOfMonth
        while (dateIterator.isBefore(lastDayOfMonth) || dateIterator.isEqual(lastDayOfMonth)) {
            datesList.add(dateIterator)
            dateIterator = dateIterator.plusDays(1)
        }
    }

    return Pair(first = datesList.map { it.dayOfMonth.toString() }, second = datesList.map { it })
}

fun getLast6Months(): Pair<List<String>, List<LocalDate>> {
    // Get today's date
    val today = LocalDate.now()

    // Get the first day of the month 6 months ago
    val firstDayOfLast6Months = today.minusMonths(5).withDayOfMonth(1)

    // Get the last day of the current month
    val lastDayOfCurrentMonth = today.withDayOfMonth(today.lengthOfMonth())

    // Create a list to store the months in the range
    val monthsInRange = mutableListOf<YearMonth>()

    // Iterate over each month in the range
    var currentMonth = YearMonth.from(firstDayOfLast6Months)
    while (!currentMonth.isAfter(YearMonth.from(lastDayOfCurrentMonth))) {
        monthsInRange.add(currentMonth)
        currentMonth = currentMonth.plusMonths(1)
    }

    // Print the range of months
    println("Range of months for the last 6 months till this month:")
    val formatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH)

    return Pair(first = monthsInRange.map { it.format(formatter) }, second = listOf())
}