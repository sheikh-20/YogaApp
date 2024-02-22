package com.bitvolper.yogazzz.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.bitvolper.yogazzz.domain.model.AppThemePreference
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface AppThemePreferenceRepository {
    val readAppThemePreference: Flow<AppThemePreference>

    suspend fun updateAppThemePreference(value: Int)
}

class GetAppThemePreferenceRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>): AppThemePreferenceRepository {
    private object PreferenceKeys {
        val APP_THEME_INDEX = intPreferencesKey("app_theme_index")
    }

    override val readAppThemePreference: Flow<AppThemePreference>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {  preference ->
                val appTheme = preference[PreferenceKeys.APP_THEME_INDEX] ?: 0
                AppThemePreference(appTheme)
            }

    override suspend fun updateAppThemePreference(value: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.APP_THEME_INDEX] = value
        }
    }
}