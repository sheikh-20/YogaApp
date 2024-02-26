package com.bitvolper.yogazzz.data.repository

import com.bitvolper.yogazzz.domain.model.AdjustYogaLevel
import com.bitvolper.yogazzz.domain.model.FaqQuestion
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.StressRelief
import com.bitvolper.yogazzz.domain.model.Subscription
import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaExercise
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

    fun getPopularYoga(): Flow<Resource<PopularYoga>>

    fun getYogaAdjustLevel(): Flow<Resource<AdjustYogaLevel>>

    fun getFlexibilityStrength(): Flow<Resource<FlexibilityStrength>>

    fun getStressRelief(): Flow<Resource<StressRelief>>

    fun getPopularYogaWithFlexibility(): Flow<Resource<PopularYogaWithFlexibility>>

    fun getYogaExercise(id: Int): Flow<Resource<YogaExercise>>

    fun getBookmarkYogaExercise(): Flow<Resource<YogaExercise>>

    suspend fun updateBookmarkYogaExercise(bookmark: Boolean)

    fun getFaqQuestion(): Flow<Resource<FaqQuestion>>

    fun getSubscription(): Flow<Resource<Subscription>>
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

    override fun getPopularYoga(): Flow<Resource<PopularYoga>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("popular_yoga").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<PopularYoga.Data>>() {}.type
            val data = Gson().fromJson<List<PopularYoga.Data>>(json, listType)

            emit(Resource.Success(PopularYoga(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getYogaAdjustLevel(): Flow<Resource<AdjustYogaLevel>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("adjust_yoga_level").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<AdjustYogaLevel.Data>>() {}.type
            val data = Gson().fromJson<List<AdjustYogaLevel.Data>>(json, listType)

            emit(Resource.Success(AdjustYogaLevel(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getFlexibilityStrength(): Flow<Resource<FlexibilityStrength>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("flexibility_yoga").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<FlexibilityStrength.Data>>() {}.type
            val data = Gson().fromJson<List<FlexibilityStrength.Data>>(json, listType)

            emit(Resource.Success(FlexibilityStrength(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
    .catch {
        Timber.tag(TAG).e(it)
        it.printStackTrace()
        emit(Resource.Failure(it))
    }

    override fun getStressRelief(): Flow<Resource<StressRelief>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("stress_relief").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<StressRelief.Data>>() {}.type
            val data = Gson().fromJson<List<StressRelief.Data>>(json, listType)

            emit(Resource.Success(StressRelief(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getPopularYogaWithFlexibility(): Flow<Resource<PopularYogaWithFlexibility>> = flow {
        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val popularYogaResult = database.getReference("app_config").child("popular_yoga").get().await()
            val adjustYogaLevelResult = database.getReference("app_config").child("adjust_yoga_level").get().await()
            val flexibilityStrengthResult = database.getReference("app_config").child("flexibility_yoga").get().await()
            val stressReliefResult = database.getReference("app_config").child("stress_relief").get().await()

            val jsonPopularYoga = Gson().toJson(popularYogaResult.value)
            Timber.tag(TAG).d("Result -> $jsonPopularYoga")
            val listTypePopularYoga = object : TypeToken<List<PopularYoga.Data>>() {}.type
            val popularYoga = Gson().fromJson<List<PopularYoga.Data>>(jsonPopularYoga, listTypePopularYoga)


            val jsonAdjustYogaLevel = Gson().toJson(adjustYogaLevelResult.value)
            Timber.tag(TAG).d("Result -> $jsonAdjustYogaLevel")
            val listTypeAdjustYogaLevel = object : TypeToken<List<AdjustYogaLevel.Data>>() {}.type
            val adjustYogaLevel = Gson().fromJson<List<AdjustYogaLevel.Data>>(jsonAdjustYogaLevel, listTypeAdjustYogaLevel)

            val jsonFlexibilityStrength = Gson().toJson(flexibilityStrengthResult.value)
            Timber.tag(TAG).d("Result -> $jsonFlexibilityStrength")
            val listTypeFlexibilityStrength = object : TypeToken<List<FlexibilityStrength.Data>>() {}.type
            val flexibilityStrength = Gson().fromJson<List<FlexibilityStrength.Data>>(jsonFlexibilityStrength, listTypeFlexibilityStrength)

            val jsonStressRelief = Gson().toJson(stressReliefResult.value)
            Timber.tag(TAG).d("Result -> $jsonStressRelief")
            val listTypeStressRelief = object : TypeToken<List<StressRelief.Data>>() {}.type
            val stressRelief = Gson().fromJson<List<StressRelief.Data>>(jsonStressRelief, listTypeStressRelief)


            emit(Resource.Success(PopularYogaWithFlexibility(
                popularYoga = PopularYoga(popularYoga),
                adjustYogaLevel = AdjustYogaLevel(adjustYogaLevel),
                flexibilityStrength = FlexibilityStrength(flexibilityStrength),
                stressRelief = StressRelief(stressRelief)
            )))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }


    override fun getYogaExercise(id: Int): Flow<Resource<YogaExercise>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")


            val result = database.getReference("yoga_exercise").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaExercise.Data>>() {}.type
            val data = Gson().fromJson<List<YogaExercise.Data>>(json, listType)

            emit(Resource.Success(YogaExercise(data = data.filter { it.id == id })))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getBookmarkYogaExercise(): Flow<Resource<YogaExercise>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("yoga_exercise").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaExercise.Data>>() {}.type
            val data = Gson().fromJson<List<YogaExercise.Data>>(json, listType)

            emit(Resource.Success(YogaExercise(data = data.filter { it.bookmark == true })))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override suspend fun updateBookmarkYogaExercise(bookmark: Boolean) {
        try {
            Timber.tag(TAG).d("Repo called")
            database.getReference("yoga_exercise").child("0").child("bookmark").setValue(bookmark)
        } catch (exception: Exception) {
            Timber.tag(TAG).e(exception)
        }
    }

    override fun getFaqQuestion(): Flow<Resource<FaqQuestion>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("faq").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<FaqQuestion.Data>>() {}.type
            val data = Gson().fromJson<List<FaqQuestion.Data>>(json, listType)

            emit(Resource.Success(FaqQuestion(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }


    override fun getSubscription(): Flow<Resource<Subscription>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = database.getReference("app_config").child("subscription").get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<Subscription.Data>>() {}.type
            val data = Gson().fromJson<List<Subscription.Data>>(json, listType)

            emit(Resource.Success(Subscription(data = data)))
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