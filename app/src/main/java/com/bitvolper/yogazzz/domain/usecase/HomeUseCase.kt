package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.HomeRepository
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.model.AdjustYogaLevel
import com.bitvolper.yogazzz.domain.model.FaqQuestion
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.Meditation
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
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
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

interface HomeUseCase {
    fun getYogaCategory(): Flow<Resource<YogaCategory>>

    fun getRecommendation(): Flow<Resource<YogaRecommendation>>

    fun getYogaCategoryWithRecommendation(): Flow<Resource<YogaCategoryWithRecommendation>>

    fun getPopularYoga(): Flow<Resource<PopularYoga>>

    fun getAdjustYogaLevel(): Flow<Resource<AdjustYogaLevel>>

    fun getFlexibilityStrength(): Flow<Resource<FlexibilityStrength>>

    fun getStressRelief(): Flow<Resource<StressRelief>>

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

    fun getUserInfo(userId: String): Flow<Resource<AccountInfo>>

    fun getHistory(id: List<AccountInfo.HistoryData>): Flow<Resource<History>>
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

    override fun getPopularYoga(): Flow<Resource<PopularYoga>> {
        return repository.getPopularYoga()
    }

    override fun getAdjustYogaLevel(): Flow<Resource<AdjustYogaLevel>> {
        return repository.getYogaAdjustLevel()
    }

    override fun getFlexibilityStrength(): Flow<Resource<FlexibilityStrength>> {
        return repository.getFlexibilityStrength()
    }

    override fun getStressRelief(): Flow<Resource<StressRelief>> {
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

    override fun getUserInfo(userId: String): Flow<Resource<AccountInfo>> {
        return repository.getUserInfo(userId)
    }

    override fun getHistory(id: List<AccountInfo.HistoryData>): Flow<Resource<History>> {
        return repository.getHistory(id)
    }
}