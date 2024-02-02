package com.bitvolper.yogazzz.presentation.notifications

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
fun NotificationScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Column(
        modifier = modifier.fillMaxSize().wrapContentSize(align = Alignment.Center)
    ) {
        Text(text = "Notification screen")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotificationLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        NotificationScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NotificationDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        NotificationScreen()
    }
}