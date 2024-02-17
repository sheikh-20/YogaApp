package com.bitvolper.yogazzz.data.repository

import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface HomeRepository {
    fun getYogaCategory(): Flow<Resource<YogaCategory>>

    fun getYogaRecommendation(): Flow<Resource<YogaRecommendation>>

    fun getYogaCategoryWithRecommendation(): Flow<Resource<YogaCategoryWithRecommendation>>
}

class HomeRepositoryImpl @Inject constructor(private val database: FirebaseDatabase): HomeRepository {
    private companion object {
        const val TAG = "HomeRepositoryImpl"
    }

    override fun getYogaCategory(): Flow<Resource<YogaCategory>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("yoga_category").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaCategory.Data>>() {}.type
            val data = Gson().fromJson<List<YogaCategory.Data>>(json, listType)

            emit(Resource.Success(YogaCategory(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getYogaRecommendation(): Flow<Resource<YogaRecommendation>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("recommendation").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaRecommendation.Data>>() {}.type
            val data = Gson().fromJson<List<YogaRecommendation.Data>>(json, listType)

            emit(Resource.Success(YogaRecommendation(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getYogaCategoryWithRecommendation(): Flow<Resource<YogaCategoryWithRecommendation>> = flow {
        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val recommendationResult = database.getReference("app_config").child("recommendation").get().await()
            val categoryResult = database.getReference("app_config").child("yoga_category").get().await()

            val jsonCategory = Gson().toJson(categoryResult.value)
            Timber.tag(TAG).d("Result -> $jsonCategory")
            val listTypeCategory = object : TypeToken<List<YogaCategory.Data>>() {}.type
            val category = Gson().fromJson<List<YogaCategory.Data>>(jsonCategory, listTypeCategory)


            val jsonRecommendation = Gson().toJson(recommendationResult.value)
            Timber.tag(TAG).d("Result -> $jsonRecommendation")
            val listTypeRecommendation = object : TypeToken<List<YogaRecommendation.Data>>() {}.type
            val recommendation = Gson().fromJson<List<YogaRecommendation.Data>>(jsonRecommendation, listTypeRecommendation)


            emit(Resource.Success(YogaCategoryWithRecommendation(category = YogaCategory(category), recommendation = YogaRecommendation(recommendation))))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
    }
}