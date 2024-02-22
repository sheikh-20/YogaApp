package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.AppThemePreferenceRepository
import com.bitvolper.yogazzz.domain.model.AppThemePreference
import kotlinx.coroutines.flow.Flow

interface AppThemeUseCase {
    val readAppThemePreference: Flow<AppThemePreference>

    suspend fun updateAppThemePreference(value: Int)
}

class GetAppThemeInteractors(private val repository: AppThemePreferenceRepository): AppThemeUseCase {
    override val readAppThemePreference: Flow<AppThemePreference>
        get() = repository.readAppThemePreference

    override suspend fun updateAppThemePreference(value: Int) = repository.updateAppThemePreference(value)

}