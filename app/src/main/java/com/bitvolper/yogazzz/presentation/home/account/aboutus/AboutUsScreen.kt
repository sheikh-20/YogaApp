package com.bitvolper.yogazzz.presentation.home.account.aboutus

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun AboutUsScreen(modifier: Modifier = Modifier,
                  paddingValues: PaddingValues = PaddingValues()) {

    val context = LocalContext.current

    val packageManager = context.packageManager
    val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
    val versionName = packageInfo.versionName

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "About:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            Text(text = "YogazzZ is the best Pocket Yoga & Fitness app for anyone who wants to Lose Weight, Get Fit, and Start a Healthy Lifestyle at home with no equipment.",
                style = MaterialTheme.typography.bodyLarge)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Version:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            Text(text = versionName)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AboutUsLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        AboutUsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AboutUsDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        AboutUsScreen()
    }
}