package com.bitvolper.yogazzz.presentation.home.reports

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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