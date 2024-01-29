package com.bitvolper.yogazzz.presentation.accountsetup

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme
import com.bitvolper.yogazzz.utility.AccountSetupContinueComposable

@Composable
fun SelectFocusAreaScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "What's your Focus Area?", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold)

        Text(text = "Where would you like to channel your energy?", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = modifier.weight(1f))

        AccountSetupContinueComposable()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectFocusAreaLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SelectFocusAreaScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SelectFocusAreaDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SelectFocusAreaScreen()
    }
}