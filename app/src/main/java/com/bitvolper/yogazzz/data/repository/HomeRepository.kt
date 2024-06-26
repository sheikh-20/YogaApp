package com.bitvolper.yogazzz.data.repository

import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.bitvolper.yogazzz.domain.model.YogaCategory
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaData
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
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

    fun getYogaRecommendation(language: String): Flow<Resource<YogaData>>

    fun getYogaCategoryWithRecommendation(language: String): Flow<Resource<YogaCategoryWithRecommendation>>

    fun getPopularYoga(language: String): Flow<Resource<YogaData>>

    fun getYogaAdjustLevel(language: String): Flow<Resource<YogaData>>

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

    fun uploadPhoto(userId: String, uri: Uri): Flow<Resource<UploadTask.TaskSnapshot>>

    fun getPhoto(userId: String): Flow<Resource<Uri>>

    fun getUserInfo(userId: String): Flow<Resource<AccountInfo>>

    fun getHistory(id: List<AccountInfo.HistoryData>, language: String): Flow<Resource<History>>

    fun getReports(id: List<AccountInfo.Reports>): Flow<Resource<Reports>>
}

class HomeRepositoryImpl @Inject constructor(private val database: FirebaseDatabase,
                                             private val firestore: FirebaseFirestore,
                                             private val storage: FirebaseStorage
): HomeRepository {
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

    override fun getYogaRecommendation(language: String): Flow<Resource<YogaData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")


            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "recommendation")
                .whereEqualTo("language", language)
                .get()
                .await()

            val filter = result.documents.map {
                Timber.tag(TAG).e(it.toString())
                it.data
            }

            val json = Gson().toJson(filter)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaData.Data>>() {}.type
            val data = Gson().fromJson<List<YogaData.Data>>(json, listType)

            emit(Resource.Success(YogaData(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getYogaCategoryWithRecommendation(language: String): Flow<Resource<YogaCategoryWithRecommendation>> = flow {
        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val recommendationResult =  firestore.collection("yoga_exercise")
                .whereEqualTo("category", "recommendation")
                .whereEqualTo("language", language)
                .get()
                .await()

            val categoryResult = database.getReference("app_config").child("yoga_category").get().await()

            val jsonCategory = Gson().toJson(categoryResult.value)
            Timber.tag(TAG).d("Result -> $jsonCategory")
            val listTypeCategory = object : TypeToken<List<YogaCategory.Data>>() {}.type
            val category = Gson().fromJson<List<YogaCategory.Data>>(jsonCategory, listTypeCategory)


            val jsonRecommendation = Gson().toJson(recommendationResult.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $jsonRecommendation")
            val listTypeRecommendation = object : TypeToken<List<YogaData.Data>>() {}.type
            val recommendation = Gson().fromJson<List<YogaData.Data>>(jsonRecommendation, listTypeRecommendation)


            emit(Resource.Success(YogaCategoryWithRecommendation(category = YogaCategory(category), recommendation = YogaData(recommendation))))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
    }

    override fun getPopularYoga(language: String): Flow<Resource<YogaData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "popularYoga")
                .whereEqualTo("language", language)
                .get()
                .await()

            val json = Gson().toJson(result.documents.map { it.data })
            Timber.tag(TAG).e("Result -> $json")

            val listType = object : TypeToken<List<YogaData.Data>>() {}.type
            val data = Gson().fromJson<List<YogaData.Data>>(json, listType)

            emit(Resource.Success(YogaData(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getYogaAdjustLevel(language: String): Flow<Resource<YogaData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "adjustYogaLevel")
                .whereEqualTo("language", language)
                .get()
                .await()

            val json = Gson().toJson(result.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaData.Data>>() {}.type
            val data = Gson().fromJson<List<YogaData.Data>>(json, listType)

            emit(Resource.Success(YogaData(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getFlexibilityStrength(language: String): Flow<Resource<YogaData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "flexibilityYoga")
                .whereEqualTo("language", language)
                .get()
                .await()

            val json = Gson().toJson(result.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaData.Data>>() {}.type
            val data = Gson().fromJson<List<YogaData.Data>>(json, listType)

            emit(Resource.Success(YogaData(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
    .catch {
        Timber.tag(TAG).e(it)
        it.printStackTrace()
        emit(Resource.Failure(it))
    }

    override fun getStressRelief(language: String): Flow<Resource<YogaData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "stressRelief")
                .whereEqualTo("language", language)
                .get()
                .await()

            val json = Gson().toJson(result.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<YogaData.Data>>() {}.type
            val data = Gson().fromJson<List<YogaData.Data>>(json, listType)

            emit(Resource.Success(YogaData(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getPopularYogaWithFlexibility(language: String): Flow<Resource<PopularYogaWithFlexibility>> = flow {
        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val popularYogaResult = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "popularYoga")
                .whereEqualTo("language", language)
                .get()
                .await()

            val adjustYogaLevelResult = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "adjustYogaLevel")
                .whereEqualTo("language", language)
                .get()
                .await()

            val flexibilityStrengthResult = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "flexibilityYoga")
                .whereEqualTo("language", language)
                .get()
                .await()

            val stressReliefResult = firestore.collection("yoga_exercise")
                .whereEqualTo("category", "stressRelief")
                .whereEqualTo("language", language)
                .get()
                .await()

            val jsonPopularYoga = Gson().toJson(popularYogaResult.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $jsonPopularYoga")
            val listTypePopularYoga = object : TypeToken<List<YogaData.Data>>() {}.type
            val popularYoga = Gson().fromJson<List<YogaData.Data>>(jsonPopularYoga, listTypePopularYoga)


            val jsonAdjustYogaLevel = Gson().toJson(adjustYogaLevelResult.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $jsonAdjustYogaLevel")
            val listTypeAdjustYogaLevel = object : TypeToken<List<YogaData.Data>>() {}.type
            val adjustYogaLevel = Gson().fromJson<List<YogaData.Data>>(jsonAdjustYogaLevel, listTypeAdjustYogaLevel)

            val jsonFlexibilityStrength = Gson().toJson(flexibilityStrengthResult.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $jsonFlexibilityStrength")
            val listTypeFlexibilityStrength = object : TypeToken<List<YogaData.Data>>() {}.type
            val flexibilityStrength = Gson().fromJson<List<YogaData.Data>>(jsonFlexibilityStrength, listTypeFlexibilityStrength)

            val jsonStressRelief = Gson().toJson(stressReliefResult.documents.map { it.data })
            Timber.tag(TAG).d("Result -> $jsonStressRelief")
            val listTypeStressRelief = object : TypeToken<List<YogaData.Data>>() {}.type
            val stressRelief = Gson().fromJson<List<YogaData.Data>>(jsonStressRelief, listTypeStressRelief)


            emit(Resource.Success(PopularYogaWithFlexibility(
                popularYoga = YogaData(popularYoga),
                adjustYogaLevel = YogaData(adjustYogaLevel),
                flexibilityStrength = YogaData(flexibilityStrength),
                stressRelief = YogaData(stressRelief)
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


    override fun getSerenityFlow(id: String, language: String): Flow<Resource<SerenityData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("id", id)
                .whereEqualTo("language", language)
                .get()
                .await()

            val filter = result.documents.map {
                Timber.tag(TAG).e(it.toString())
                it.data
            }

            val json = Gson().toJson(filter)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<SerenityData.Data>>() {}.type
            val data = Gson().fromJson<List<SerenityData.Data>>(json, listType)

            emit(Resource.Success(SerenityData(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getBookmarkYogaExercise(id: List<String>, language: String): Flow<Resource<SerenityData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereIn("id", id)
                .whereEqualTo("language", language)
                .get()
                .await()

            val filter = result.documents.map {
                Timber.tag(TAG).e(it.toString())
                it.data
            }

            val json = Gson().toJson(filter)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<SerenityData.Data>>() {}.type
            val data = Gson().fromJson<List<SerenityData.Data>>(json, listType)

            emit(Resource.Success(SerenityData(data = data)))
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


    override fun getMeditation(language: String): Flow<Resource<Meditation>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("type", "meditation")
                .whereEqualTo("language", language)
                .get()
                .await()

            val filter = result.documents.map {
                Timber.tag(TAG).e(it.toString())
                it.data
            }

            val json = Gson().toJson(filter)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<Meditation.Data>>() {}.type
            val data = Gson().fromJson<List<Meditation.Data>>(json, listType)

            emit(Resource.Success(Meditation(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }


    override fun getHistory(id: List<AccountInfo.HistoryData>, language: String): Flow<Resource<History>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereIn("id", id.map { it.id })
                .whereEqualTo("language", language)
                .get()
                .await()

            val filter = result.documents.map {
                Timber.tag(TAG).e(it.toString())
                it.data
            }

            val json = Gson().toJson(filter)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<History.Data>>() {}.type
            val data = Gson().fromJson<List<History.Data>>(json, listType)

            val date = id.map { it.id }

            val alteredResult = data.map {
                Timber.tag(TAG).d("Date -> " + it.date)
                it.copy(date = if (date.contains(it.id)) {
                    id.first { historyData -> historyData.id == it.id }.date
                } else "")
            }

            Timber.tag(TAG).d(alteredResult.toString())
            emit(Resource.Success(History(data = alteredResult)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getReports(id: List<AccountInfo.Reports>): Flow<Resource<Reports>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")

            val result = firestore.collection("yoga_exercise")
                .whereIn("id", id.map { it.id })
                .get()
                .await()

            val filter = result.documents.map {
                Timber.tag(TAG).e(it.toString())
                it.data
            }

            val json = Gson().toJson(filter)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<Reports.Data>>() {}.type
            val data = Gson().fromJson<List<Reports.Data>>(json, listType)

            Timber.tag(TAG).d(data.toString())
            emit(Resource.Success(Reports(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }


    override fun uploadPhoto(userId: String, uri: Uri): Flow<Resource<UploadTask.TaskSnapshot>> =
        flow {
            emit(Resource.Loading)

            val storageUri = storage.reference.child(userId).putFile(uri).await()
            emit(Resource.Success(storageUri))
        }
            .catch {
                Timber.tag(TAG).e(it)
                it.printStackTrace()
                emit(Resource.Failure(it))
            }

    override fun getPhoto(userId: String): Flow<Resource<Uri>> = flow {
        emit(Resource.Loading)

        val storageUri = storage.reference.child(userId).downloadUrl.await()

        Timber.tag(TAG).d(storageUri.toString())
        emit(Resource.Success(storageUri))
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override fun getYogaExerciseByCategory(category: String, language: String): Flow<Resource<SerenityData>> = flow {

        Timber.tag(TAG).d("Called")
        emit(Resource.Loading)

        try {

            Timber.tag(TAG).d("Repo called")


            val result = firestore.collection("yoga_exercise")
                .whereEqualTo("category", category)
                .whereEqualTo("language", language)
                .get()
                .await()

            val filter = result.documents.map {
                Timber.tag(TAG).e(it.toString())
                it.data
            }

            val json = Gson().toJson(filter)
            Timber.tag(TAG).d("Result -> $json")

            val listType = object : TypeToken<List<SerenityData.Data>>() {}.type
            val data = Gson().fromJson<List<SerenityData.Data>>(json, listType)

            emit(Resource.Success(SerenityData(data = data)))
        } catch (exception: Exception) {
            throw Throwable(exception)
        }
    }
        .catch {
            Timber.tag(TAG).e(it)
            it.printStackTrace()
            emit(Resource.Failure(it))
        }

    override suspend fun updateUserInfo(userId: String, accountInfo: AccountInfo) {
        database.getReference("user").child(userId).setValue(accountInfo)
    }

    override fun getUserInfo(userId: String): Flow<Resource<AccountInfo>> = flow {
        emit(Resource.Loading)

        try {
            val result = database.getReference("user").child(userId).get().await()

            val json = Gson().toJson(result.value)
            Timber.tag(TAG).d(json)

            emit(Resource.Success(Gson().fromJson(json, AccountInfo::class.java)))
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