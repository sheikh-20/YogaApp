package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.HomeRepository
import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.utility.Resource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

interface HomeUseCase {
    fun getYogaCategory(): Flow<Resource<YogaCategory>>

    fun getRecommendation(): Flow<Resource<YogaRecommendation>>

    fun getYogaCategoryWithRecommendation(): Flow<Resource<YogaCategoryWithRecommendation>>
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
}