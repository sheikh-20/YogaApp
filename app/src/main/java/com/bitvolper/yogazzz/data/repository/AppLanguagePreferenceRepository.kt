package com.bitvolper.yogazzz.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.bitvolper.yogazzz.domain.model.AppLanguagePreference
import com.bitvolper.yogazzz.domain.model.AppThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

interface AppLanguagePreferenceRepository {
    val readLanguagePreference: Flow<AppLanguagePreference>
    suspend fun updateLanguagePreference(value: Int)
}

class GetAppLanguagePreferenceRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>): AppLanguagePreferenceRepository {

    private object PreferenceKeys {
        val APP_LANGUAGE_INDEX = intPreferencesKey("app_language_index")
    }

    override val readLanguagePreference: Flow<AppLanguagePreference>
        get() = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map {  preference ->
                val appTheme = preference[PreferenceKeys.APP_LANGUAGE_INDEX] ?: 0
                AppLanguagePreference(appTheme)
            }

    override suspend fun updateLanguagePreference(value: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.APP_LANGUAGE_INDEX] = value
        }
    }

}