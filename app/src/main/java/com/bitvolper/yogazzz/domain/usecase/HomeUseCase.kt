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

    fun getRecommendation(): Flow<Resource<YogaRecommendation>>

    fun getYogaCategoryWithRecommendation(): Flow<Resource<YogaCategoryWithRecommendation>>

    fun getPopularYoga(): Flow<Resource<YogaData>>

    fun getAdjustYogaLevel(): Flow<Resource<YogaData>>

    fun getFlexibilityStrength(): Flow<Resource<YogaData>>

    fun getStressRelief(): Flow<Resource<YogaData>>

    fun getPopularYogaWithFlexibility(): Flow<Resource<PopularYogaWithFlexibility>>

    fun getYogaExercise(id: Int): Flow<Resource<YogaExercise>>

    fun getSerenityFlow(id: String): Flow<Resource<SerenityData>>

    fun getBookmarkYogaExercise(id: List<String>): Flow<Resource<SerenityData>>

    suspend fun updateBookmarkYogaExercise(bookmark: Boolean)

    fun getFaqQuestion(): Flow<Resource<FaqQuestion>>

    fun getSubscription(): Flow<Resource<Subscription>>

    fun getMeditation(): Flow<Resource<Meditation>>

    fun getYogaExerciseByCategory(category: String): Flow<Resource<SerenityData>>

    suspend fun updateUserInfo(userId: String, accountInfo: AccountInfo)

    fun uploadProfilePhoto(userId: String, uri: Uri): Flow<Resource<UploadTask.TaskSnapshot>>

    fun getPhoto(userId: String): Flow<Resource<Uri>>

    fun getUserInfo(userId: String): Flow<Resource<AccountInfo>>

    fun getHistory(id: List<AccountInfo.HistoryData>): Flow<Resource<History>>

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

    override fun getRecommendation(): Flow<Resource<YogaRecommendation>> {
        return repository.getYogaRecommendation()
    }

    override fun getYogaCategoryWithRecommendation(): Flow<Resource<YogaCategoryWithRecommendation>> {
        return repository.getYogaCategoryWithRecommendation()
    }

    override fun getPopularYoga(): Flow<Resource<YogaData>> {
        return repository.getPopularYoga()
    }

    override fun getAdjustYogaLevel(): Flow<Resource<YogaData>> {
        return repository.getYogaAdjustLevel()
    }

    override fun getFlexibilityStrength(): Flow<Resource<YogaData>> {
        return repository.getFlexibilityStrength()
    }

    override fun getStressRelief(): Flow<Resource<YogaData>> {
        return repository.getStressRelief()
    }

    override fun getPopularYogaWithFlexibility(): Flow<Resource<PopularYogaWithFlexibility>> {
        return repository.getPopularYogaWithFlexibility()
    }

    override fun getYogaExercise(id: Int): Flow<Resource<YogaExercise>> {
        return repository.getYogaExercise(id)
    }

    override fun getSerenityFlow(id: String): Flow<Resource<SerenityData>> {
        return repository.getSerenityFlow(id)
    }

    override fun getBookmarkYogaExercise(id: List<String>): Flow<Resource<SerenityData>> {
        return repository.getBookmarkYogaExercise(id)
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

    override fun getMeditation(): Flow<Resource<Meditation>> {
        return repository.getMeditation()
    }

    override fun getYogaExerciseByCategory(category: String): Flow<Resource<SerenityData>> {
        return repository.getYogaExerciseByCategory(category)
    }

    override suspend fun updateUserInfo(userId: String, accountInfo: AccountInfo) {
        repository.updateUserInfo(userId, accountInfo)
    }

    override fun uploadProfilePhoto(userId: String, uri: Uri): Flow<Resource<UploadTask.TaskSnapshot>> = repository.uploadPhoto(userId, uri)

    override fun getPhoto(userId: String): Flow<Resource<Uri>> = repository.getPhoto(userId)

    override fun getUserInfo(userId: String): Flow<Resource<AccountInfo>> {
        return repository.getUserInfo(userId)
    }

    override fun getHistory(id: List<AccountInfo.HistoryData>): Flow<Resource<History>> {
        return repository.getHistory(id)
    }

    override fun getReports(id: List<AccountInfo.Reports>): Flow<Resource<Reports>> {
        return repository.getReports(id)
    }
}