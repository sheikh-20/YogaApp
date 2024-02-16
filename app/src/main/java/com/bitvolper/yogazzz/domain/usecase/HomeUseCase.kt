package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.HomeRepository
import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.utility.Resource
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getYogaCategory(): Flow<Resource<YogaCategory>>
}

class GetHomeUseCaseInteractors(private val repository: HomeRepository): HomeUseCase {

    private companion object {
        const val TAG = "HomeUseCase"
    }
    
    override fun getYogaCategory(): Flow<Resource<YogaCategory>> = repository.getYogaCategory()
}