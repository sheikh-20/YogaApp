package com.bitvolper.yogazzz.presentation.analytics

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun AnalyticsScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row {
            Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Data Usage", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = "Control how your data is used for analytics. Customize your preferences.", style = MaterialTheme.typography.bodyMedium)
            }

            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
        }

        Row {
            Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Download My Data", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = "Request a copy of your data. Your information, your control", style = MaterialTheme.typography.bodyMedium)
            }

            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
        }

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