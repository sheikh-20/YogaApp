package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.AppLanguagePreferenceRepository
import com.bitvolper.yogazzz.domain.model.AppLanguagePreference
import kotlinx.coroutines.flow.Flow

interface AppLanguageUseCase {
    val readLanguagePreference: Flow<AppLanguagePreference>
    suspend fun updateLanguagePreference(value: Int)
}

class GetAppLanguageInteractors(private val repository: AppLanguagePreferenceRepository): AppLanguageUseCase {
    override val readLanguagePreference: Flow<AppLanguagePreference>
        get() = repository.readLanguagePreference

    override suspend fun updateLanguagePreference(value: Int) = repository.updateLanguagePreference(value)

}