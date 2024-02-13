package com.bitvolper.yogazzz.presentation.serenitydetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun SerenityDetailScreen(modifier: Modifier = Modifier,
                         paddingValues: PaddingValues = PaddingValues()) {

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )) {

        Text(text = "Serenity Detail screen")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SerenityDetailLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SerenityDetailScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SerenityDetailDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SerenityDetailScreen()
    }
}