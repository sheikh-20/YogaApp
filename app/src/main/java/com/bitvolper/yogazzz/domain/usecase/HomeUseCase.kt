package com.bitvolper.yogazzz.domain.usecase

import android.net.Uri
import com.bitvolper.yogazzz.data.repository.HomeRepository
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.model.AdjustYogaLevel
import com.bitvolper.yogazzz.domain.model.FaqQuestion
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.Meditation
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.Reports
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.domain.model.StressRelief
import com.bitvolper.yogazzz.domain.model.Subscription
import com.bitvolper.yogazzz.domain.model.UserData
import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaData
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

interface HomeUseCase {
    fun getYogaCategory(): Flow<Resource<YogaCategory>>

    fun getRecommendation(language: String): Flow<Resource<YogaData>>

    fun getYogaCategoryWithRecommendation(language: String): Flow<Resource<YogaCategoryWithRecommendation>>

    fun getPopularYoga(language: String): Flow<Resource<YogaData>>

    fun getAdjustYogaLevel(language: String): Flow<Resource<YogaData>>

    fun getFlexibilityStrength(language: String): Flow<Resource<YogaData>>

    fun getStressRelief(language: String): Flow<Resource<YogaData>>

    fun getPopularYogaWithFlexibility(language: String): Flow<Resource<PopularYogaWithFlexibility>>

    fun getYogaExercise(id: Int): Flow<Resource<YogaExercise>>

    fun getSerenityFlow(id: String, language: String): Flow<Resource<SerenityData>>

    fun getBookmarkYogaExercise(id: List<String>, language: String): Flow<Resource<SerenityData>>

    suspend fun updateBookmarkYogaExercise(bookmark: Boolean)

    fun getFaqQuestion(): Flow<Resource<FaqQuestion>>

    fun getSubscription(): Flow<Resource<Subscription>>

    fun getMeditation(language: String): Flow<Resource<Meditation>>

    fun getYogaExerciseByCategory(category: String, language: String): Flow<Resource<SerenityData>>

    suspend fun updateUserInfo(userId: String, accountInfo: AccountInfo)

    fun uploadProfilePhoto(userId: String, uri: Uri): Flow<Resource<UploadTask.TaskSnapshot>>

    fun getPhoto(userId: String): Flow<Resource<Uri>>

    fun getUserInfo(userId: String): Flow<Resource<AccountInfo>>

    fun getHistory(id: List<AccountInfo.HistoryData>, language: String): Flow<Resource<History>>

    fun getReports(id: List<AccountInfo.Reports>): Flow<Resource<Reports>>
}

class GetHomeUseCaseInteractors(private val repository: HomeRepository): HomeUseCase {

    private companion object {
        const val TAG = "HomeUseCase"
    }
    
    override fun getYogaCategory(): Flow<Resource<YogaCategory>> {
        Timber.tag(TAG).d("Interactor called")
        return repository.getYogaCategory()
    }

    override fun getRecommendation(language: String): Flow<Resource<YogaData>> {
        return repository.getYogaRecommendation(language)
    }

    override fun getYogaCategoryWithRecommendation(language: String): Flow<Resource<YogaCategoryWithRecommendation>> {
        return repository.getYogaCategoryWithRecommendation(language)
    }

    override fun getPopularYoga(language: String): Flow<Resource<YogaData>> {
        return repository.getPopularYoga(language)
    }

    override fun getAdjustYogaLevel(language: String): Flow<Resource<YogaData>> {
        return repository.getYogaAdjustLevel(language)
    }

    override fun getFlexibilityStrength(language: String): Flow<Resource<YogaData>> {
        return repository.getFlexibilityStrength(language)
    }

    override fun getStressRelief(language: String): Flow<Resource<YogaData>> {
        return repository.getStressRelief(language)
    }

    override fun getPopularYogaWithFlexibility(language: String): Flow<Resource<PopularYogaWithFlexibility>> {
        return repository.getPopularYogaWithFlexibility(language)
    }

    override fun getYogaExercise(id: Int): Flow<Resource<YogaExercise>> {
        return repository.getYogaExercise(id)
    }

    override fun getSerenityFlow(id: String, language: String): Flow<Resource<SerenityData>> {
        return repository.getSerenityFlow(id, language)
    }

    override fun getBookmarkYogaExercise(id: List<String>, language: String): Flow<Resource<SerenityData>> {
        return repository.getBookmarkYogaExercise(id, language)
    }

    override suspend fun updateBookmarkYogaExercise(bookmark: Boolean) {
        repository.updateBookmarkYogaExercise(bookmark)
    }

    override fun getFaqQuestion(): Flow<Resource<FaqQuestion>> {
        return repository.getFaqQuestion()
    }

    override fun getSubscription(): Flow<Resource<Subscription>> {
        return repository.getSubscription()
    }

    override fun getMeditation(language: String): Flow<Resource<Meditation>> {
        return repository.getMeditation(language)
    }

    override fun getYogaExerciseByCategory(category: String, language: String): Flow<Resource<SerenityData>> {
        return repository.getYogaExerciseByCategory(category, language)
    }

    override suspend fun updateUserInfo(userId: String, accountInfo: AccountInfo) {
        repository.updateUserInfo(userId, accountInfo)
    }

    override fun uploadProfilePhoto(userId: String, uri: Uri): Flow<Resource<UploadTask.TaskSnapshot>> = repository.uploadPhoto(userId, uri)

    override fun getPhoto(userId: String): Flow<Resource<Uri>> = repository.getPhoto(userId)

    override fun getUserInfo(userId: String): Flow<Resource<AccountInfo>> {
        return repository.getUserInfo(userId)
    }

    override fun getHistory(id: List<AccountInfo.HistoryData>, language: String): Flow<Resource<History>> {
        return repository.getHistory(id, language)
    }

    override fun getReports(id: List<AccountInfo.Reports>): Flow<Resource<Reports>> {
        return repository.getReports(id)
    }
}