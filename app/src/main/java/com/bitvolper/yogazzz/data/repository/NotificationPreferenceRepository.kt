package com.bitvolper.yogazzz.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface NotificationPreferenceRepository {
    val readDailyReminder: Flow<NotificationPreference>
    val readFeedbackAppUpdates: Flow<NotificationPreference>

    suspend fun saveDailyReminderPreference(isEnabled: Boolean)
    suspend fun saveFeedbackAppUpdatePreference(isEnabled: Boolean)

}

class GetNotificationPreferenceImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : NotificationPreferenceRepository {

    private object PreferenceKeys {
        val IS_DAILY_UPDATES = booleanPreferencesKey("is_daily_updates")
        val IS_FEEDBACK_APP_UPDATES = booleanPreferencesKey("is_feedback_app_updates")
    }

    override val readDailyReminder: Flow<NotificationPreference>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {  preference ->
                val isEnabled = preference[PreferenceKeys.IS_DAILY_UPDATES] ?: true
                NotificationPreference(isEnabled)
            }

    override val readFeedbackAppUpdates: Flow<NotificationPreference>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {  preference ->
                val isEnabled = preference[PreferenceKeys.IS_FEEDBACK_APP_UPDATES] ?: true
                NotificationPreference(isEnabled)
            }

    override suspend fun saveDailyReminderPreference(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_DAILY_UPDATES] = isEnabled
        }
    }

    override suspend fun saveFeedbackAppUpdatePreference(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_FEEDBACK_APP_UPDATES] = isEnabled
        }
    }
}