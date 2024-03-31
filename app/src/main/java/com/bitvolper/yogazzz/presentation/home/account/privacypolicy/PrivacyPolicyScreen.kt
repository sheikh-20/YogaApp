package com.bitvolper.yogazzz.presentation.home.account.privacypolicy

import android.content.res.Configuration
import android.os.Build
import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun PrivacyPolicyScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {

    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Text(text = Html.fromHtml(stringResource(id = R.string.privacy_policy), Html.FROM_HTML_MODE_COMPACT).toString())
        } else {
            Text(text = Html.fromHtml(stringResource(id = R.string.privacy_policy)).toString())

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PrivacyPolicyLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        PrivacyPolicyScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PrivacyPolicyDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        PrivacyPolicyScreen()
    }
}