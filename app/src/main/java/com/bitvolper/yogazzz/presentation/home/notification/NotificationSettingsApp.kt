package com.bitvolper.yogazzz.presentation.home.notification

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitvolper.yogazzz.R
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import com.bitvolper.yogazzz.presentation.viewmodel.AccountViewModel

@Composable
fun NotificationSettingsApp(modifier: Modifier = Modifier, accountViewModel: AccountViewModel = hiltViewModel()) {

    val dailyReminderUIState: NotificationPreference by accountViewModel.isDailyReminderEnabled.collectAsState(initial = NotificationPreference(true))
    val feedbackAppUpdatesUIState: NotificationPreference by accountViewModel.isFeedbackAppUpdatesEnabled.collectAsState(initial = NotificationPreference(true))

    Scaffold(
        topBar = { NotificationSettingsTopAppBar() }
    ) { paddingValues ->
        NotificationSettingsScreen(
            paddingValues = paddingValues,
            readDailyReminder = dailyReminderUIState,
            readFeedbackAppUpdates = feedbackAppUpdatesUIState,
            onSaveDailyReminderPreference = accountViewModel::saveDailyReminderPreference,
            onSaveFeedbackAppUpdatePreference = accountViewModel::saveFeedbackAppUpdatePreference
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationSettingsTopAppBar() {

    val context = LocalContext.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.notification),
                fontWeight = FontWeight.SemiBold)
        },
        navigationIcon = {
            IconButton(onClick = { (context as Activity).finish() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )
}