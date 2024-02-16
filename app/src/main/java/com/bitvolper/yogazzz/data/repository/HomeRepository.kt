package com.bitvolper.yogazzz.data.repository

import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface HomeRepository {
    fun getYogaCategory(): Flow<Resource<YogaCategory>>
}

class HomeRepositoryImpl @Inject constructor(private val database: FirebaseDatabase): HomeRepository {
    private companion object {
        const val TAG = "HomeRepositoryImpl"
    }

    override fun getYogaCategory(): Flow<Resource<YogaCategory>> = flow {
        emit(Resource.Loading)

        try {
            val result = database.getReference("app_config").child("yoga_category").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val data = Gson().fromJson(json, YogaCategory::class.java)

            emit(Resource.Success(data))
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