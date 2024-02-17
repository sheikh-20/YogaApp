package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.NotificationPreferenceRepository
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import kotlinx.coroutines.flow.Flow

interface PushNotificationUseCase {
    val readDailyReminder: Flow<NotificationPreference>
    val readFeedbackAppUpdates: Flow<NotificationPreference>

    suspend fun saveDailyReminderPreference(isEnabled: Boolean)
    suspend fun saveFeedbackAppUpdatePreference(isEnabled: Boolean)

}

class GetPushNotificationInteractors(private val repository: NotificationPreferenceRepository): PushNotificationUseCase {
    override val readDailyReminder: Flow<NotificationPreference>
        get() = repository.readDailyReminder
    override val readFeedbackAppUpdates: Flow<NotificationPreference>
        get() = repository.readFeedbackAppUpdates

    override suspend fun saveDailyReminderPreference(isEnabled: Boolean) = repository.saveDailyReminderPreference(isEnabled)

    override suspend fun saveFeedbackAppUpdatePreference(isEnabled: Boolean) = repository.saveFeedbackAppUpdatePreference(isEnabled)

}