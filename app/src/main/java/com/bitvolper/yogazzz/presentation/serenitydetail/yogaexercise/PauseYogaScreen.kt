package com.bitvolper.yogazzz.presentation.serenitydetail.yogaexercise

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun PauseYogaScreen(modifier: Modifier = Modifier,
                    paddingValues: PaddingValues = PaddingValues()) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PauseYogaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        PauseYogaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PauseYogaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        PauseYogaScreen()
    }
}
