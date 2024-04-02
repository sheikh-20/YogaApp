package com.bitvolper.yogazzz.presentation.support

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.presentation.faq.FaqActivity
import com.bitvolper.yogazzz.presentation.home.account.aboutus.AboutUsActivity
import com.bitvolper.yogazzz.presentation.home.account.privacypolicy.PrivacyPolicyActivity
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun SupportScreen(modifier: Modifier = Modifier, paddingValues: PaddingValues = PaddingValues()) {

    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "FAQ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))

            IconButton(onClick = { FaqActivity.startActivity(context as Activity) }) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Privacy Policy", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))

            IconButton(onClick = { PrivacyPolicyActivity.startActivity(context as Activity) }) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "About us", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))

            IconButton(onClick = { AboutUsActivity.startActivity(context as Activity) }) {
                Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SupportLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        SupportScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SupportDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        SupportScreen()
    }
}