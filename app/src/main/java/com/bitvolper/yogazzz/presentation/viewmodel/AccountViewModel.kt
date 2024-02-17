package com.bitvolper.yogazzz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import com.bitvolper.yogazzz.domain.usecase.PushNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val pushNotificationUseCase: PushNotificationUseCase) : ViewModel() {

    val isDailyReminderEnabled: Flow<NotificationPreference> = pushNotificationUseCase.readDailyReminder.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = NotificationPreference(true)
    )

    val isFeedbackAppUpdatesEnabled: Flow<NotificationPreference> = pushNotificationUseCase.readFeedbackAppUpdates.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = NotificationPreference(true)
    )

    fun saveDailyReminderPreference(value: Boolean) = viewModelScope.launch {
        pushNotificationUseCase.saveDailyReminderPreference(value)
    }

    fun saveFeedbackAppUpdatePreference(value: Boolean) = viewModelScope.launch {
        pushNotificationUseCase.saveFeedbackAppUpdatePreference(value)
    }

}