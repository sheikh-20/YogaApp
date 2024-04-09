package com.bitvolper.yogazzz.presentation.accountsecurity

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun AccountSecurityScreen(modifier: Modifier = Modifier,
                          paddingValues: PaddingValues = PaddingValues(),
                          onDeactivateClick: () -> Unit = {  },
                          onDeleteAccountClick: () -> Unit = {  }) {
    Column(modifier = modifier
        .fillMaxSize()
        .padding(
            top = paddingValues.calculateTopPadding(),
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {


//        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//            Text(text = "Biometric ID", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))
//            Switch(checked = false, onCheckedChange = { })
//        }
//
//
//        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//            Text(text = "Face ID", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = modifier.weight(1f))
//            Switch(checked = false, onCheckedChange = { })
//        }
//
//        Row {
//            Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                Text(text = "Device Management", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
//                Text(text = "Manage your account on various devices you own.", style = MaterialTheme.typography.bodyMedium)
//            }
//
//            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos, contentDescription = null)
//        }


        Row(modifier = modifier.clickable(
            onClick = onDeactivateClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null)) {
            Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = stringResource(R.string.deactivate_account), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(text = stringResource(R.string.temporarily_deactivate_your_account_easily_activate_when_you_re_ready), style = MaterialTheme.typography.bodyMedium)
            }

            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
        }

        Row(modifier = modifier.clickable(
            onClick = onDeleteAccountClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null)) {
            Column(modifier = modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = stringResource(R.string.delete_account), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Color.Red)
                Text(text = stringResource(R.string.permanently_remove_your_account_and_data_proceed_with_caution), style = MaterialTheme.typography.bodyMedium)
            }

            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = null)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AccountLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        AccountSecurityScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AccountDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        AccountSecurityScreen()
    }
}