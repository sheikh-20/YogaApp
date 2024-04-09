package com.bitvolper.yogazzz.presentation.appearance

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun AppearanceScreen(modifier: Modifier = Modifier,
                     paddingValues: PaddingValues = PaddingValues(),
                     onThemeClick: () -> Unit = {  },
                     appThemeIndex: Int = 0,

                     onLanguageClick: () -> Unit = { },
                     appLanguageIndex: Int = 0) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = stringResource(R.string.theme), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))

            Text(text = when(appThemeIndex) {
                0 -> {
                    stringResource(R.string.system_default) }
                1 -> {
                    stringResource(R.string.light) }
                else -> {
                    stringResource(R.string.dark) }
                                            }, style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = onThemeClick, modifier = modifier.then(modifier.size(30.dp))) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = stringResource(R.string.app_language), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))

            Text(text = when(appLanguageIndex) {
                0 -> {
                    stringResource(R.string.english) }
                else -> {
                    stringResource(R.string.spanish) } }, style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = onLanguageClick, modifier = modifier.then(modifier.size(30.dp))) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AppearanceLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        AppearanceScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppearanceDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        AppearanceScreen()
    }
}