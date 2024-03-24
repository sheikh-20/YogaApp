package com.bitvolper.yogazzz.presentation.home.reports

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.model.CartesianLayerModel
import com.patrykandpatrick.vico.core.model.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.model.LineCartesianLayerModel


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
                text = { Text(text = "Last Month") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("Last Month")
                })

            DropdownMenuItem(
                text = { Text(text = "Last 3 Months") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("Last 3 Months")
                })

            DropdownMenuItem(
                text = { Text(text = "Last 6 Months") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("Last 6 Months")
                })

            DropdownMenuItem(
                text = { Text(text = "This Year") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("This Year")
                })

            DropdownMenuItem(
                text = { Text(text = "Last Year") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("Last Year")
                })

            DropdownMenuItem(
                text = { Text(text = "All Time") },
                onClick = {
                    onExpand(false)
                    onSelectPeriod("All Time")
                })
        }
    }
}