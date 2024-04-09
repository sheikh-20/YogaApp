package com.bitvolper.yogazzz.presentation.home.notification

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import com.bitvolper.yogazzz.presentation.theme.YogaAppTheme

@Composable
fun NotificationSettingsScreen(modifier: Modifier = Modifier,
                               paddingValues: PaddingValues = PaddingValues(),
                               readDailyReminder: NotificationPreference = NotificationPreference(true),
                               readFeedbackAppUpdates: NotificationPreference = NotificationPreference(true),
                               onSaveDailyReminderPreference: (Boolean) -> Unit = { _ -> },
                               onSaveFeedbackAppUpdatePreference: (Boolean) -> Unit = { _ -> }) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.daily_reminder),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(1f)
            )
            Switch(checked = readDailyReminder.isEnabled, onCheckedChange = onSaveDailyReminderPreference)
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.new_content_alerts),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(1f)
            )
            Switch(checked = false, onCheckedChange = {  })
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.subscription_renewal_reminders),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(1f)
            )
            Switch(checked = false, onCheckedChange = {  })
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.progress_milestones),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(1f)
            )
            Switch(checked = false, onCheckedChange = {  })
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.achievement_unlocks),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(1f)
            )
            Switch(checked = false, onCheckedChange = {  })
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.customer_support_updates),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(1f)
            )
            Switch(checked = false, onCheckedChange = {  })
        }

        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Text(
                text = stringResource(R.string.feedback_updates),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.weight(1f)
            )
            Switch(checked = readFeedbackAppUpdates.isEnabled, onCheckedChange = onSaveFeedbackAppUpdatePreference)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotificationSettingsLightThemePreview() {
    YogaAppTheme(darkTheme = false) {
        NotificationSettingsScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NotificationSettingsDarkThemePreview() {
    YogaAppTheme(darkTheme = true) {
        NotificationSettingsScreen()
    }
}