package com.bitvolper.yogazzz.presentation.home.history

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {

    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier.fillMaxSize().padding(top = paddingValues.calculateTopPadding(),
        bottom = paddingValues.calculateBottomPadding(), start = 16.dp, end = 16.dp)) {

        DatePicker(state = datePickerState)
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