package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun NextYogaScreen(modifier: Modifier = Modifier,
                   paddingValues: PaddingValues = PaddingValues()) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NextYogaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        NextYogaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NextYogaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        NextYogaScreen()
    }
}