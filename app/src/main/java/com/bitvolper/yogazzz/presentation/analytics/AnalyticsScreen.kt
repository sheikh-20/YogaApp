package com.bitvolper.yogazzz.presentation.analytics

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun AnalyticsScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Column(modifier = modifier.fillMaxSize().wrapContentSize(align = Alignment.Center)) {
        Text(text = "Analytics screen")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AnalyticsLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        AnalyticsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AnalyticsDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        AnalyticsScreen()
    }
}