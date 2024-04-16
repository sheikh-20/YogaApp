package com.bitvolper.yogazzz.presentation.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.model.AppLanguagePreference
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.Reports
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.domain.model.UserData
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaData
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.domain.usecase.AppLanguageUseCase
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase,
                                        private val appLanguageUseCase: AppLanguageUseCase): ViewModel() {

    private companion object {
        const val TAG = "HomeViewModel"
    }

    private val auth = Firebase.auth

    private var _profileInfoUiState = MutableStateFlow<UserData>(UserData(userId = "", userName = "", profilePictureUrl = "", email = ""))
    val profileInfoUiState: StateFlow<UserData> = _profileInfoUiState


    private var _homeUIState = MutableStateFlow<Resource<YogaCategoryWithRecommendation>>(Resource.Loading)
    val homeUIState: StateFlow<Resource<YogaCategoryWithRecommendation>> get() = _homeUIState

    private var _recommendationUIState = MutableStateFlow<Resource<YogaRecommendation>>(Resource.Loading)
    val recommendationUIState: StateFlow<Resource<YogaRecommendation>> get() = _recommendationUIState

    private var _yogaExerciseUIState = MutableStateFlow<Resource<YogaExercise>>(Resource.Loading)
    val yogaExerciseUIState: StateFlow<Resource<YogaExercise>> get() = _yogaExerciseUIState

    private var _serenityFlowUIState = MutableStateFlow<Resource<SerenityData>>(Resource.Loading)
    val serenityFlowUIState: StateFlow<Resource<SerenityData>> get() = _serenityFlowUIState

    private var _bookmarkUIState = MutableStateFlow<Resource<SerenityData>>(Resource.Loading)
    val bookmarkUIState: StateFlow<Resource<SerenityData>> get() = _bookmarkUIState

    private var _yogaCategoryUIState = MutableStateFlow<Resource<SerenityData>>(Resource.Loading)
    val yogaCategoryUIState: StateFlow<Resource<SerenityData>> get() = _yogaCategoryUIState

    private var _historyUIState = MutableStateFlow<Resource<History>>(Resource.Loading)
    val historyUIState: StateFlow<Resource<History>> get() = _historyUIState

    private var _reportsUIState = MutableStateFlow<Resource<Reports>>(Resource.Loading)
    val reportsUIState: StateFlow<Resource<Reports>> get() = _reportsUIState

    val appLanguageIndex: Flow<AppLanguagePreference> = appLanguageUseCase.readLanguagePreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = AppLanguagePreference(0)
    )


    private fun getSignedInUser() {
        auth.currentUser?.apply {
            _profileInfoUiState.value = UserData(
                userId = uid,
                userName = displayName,
                profilePictureUrl = photoUrl.toString(),
                email = email
            )
        }
    }

    fun getHomeContent() = viewModelScope.launch {
        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getYogaCategoryWithRecommendation("en").collectLatest { data ->
                            _homeUIState.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getYogaCategoryWithRecommendation("es").collectLatest { data ->
                            _homeUIState.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getYogaExerciseByCategory(category: String = "improvedFlexibility") = viewModelScope.launch {
        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getYogaExerciseByCategory(category, "en").collectLatest {
                            _yogaCategoryUIState.value = it
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getYogaExerciseByCategory(category, "es").collectLatest {
                            _yogaCategoryUIState.value = it
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getYogaRecommendation() = viewModelScope.launch {

        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getRecommendation("en").collectLatest { data ->
                            _recommendationUIState.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getRecommendation("es").collectLatest { data ->
                            _recommendationUIState.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getYogaExercise(id: Int = 1) = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getYogaExercise(id).collectLatest {
                _yogaExerciseUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getSerenityFlow(id: String = "c0fdad9b-307c-4000-a342-346cb8f8abac") = viewModelScope.launch {
        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getSerenityFlow(id, "en").collectLatest { data ->
                            _serenityFlowUIState.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getSerenityFlow(id, "es").collectLatest { data ->
                            _serenityFlowUIState.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getBookmark(id: List<String>) = viewModelScope.launch {
        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getBookmarkYogaExercise(id, "en").collectLatest { data ->
                            _bookmarkUIState.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getBookmarkYogaExercise(id, "es").collectLatest { data ->
                            _bookmarkUIState.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun resetBookmark() {
        _bookmarkUIState.value = Resource.Success(SerenityData(emptyList()))
    }

    fun getHistory(id: List<AccountInfo.HistoryData>) = viewModelScope.launch {
        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getHistory(id, "en").collectLatest { data ->
                            _historyUIState.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getHistory(id, "es").collectLatest { data ->
                            _historyUIState.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun resetHistory() {
        _historyUIState.value = Resource.Success(History(emptyList()))
    }

    fun getReports(id: List<AccountInfo.Reports>) = viewModelScope.launch {
        try {
            homeUseCase.getReports(id).collectLatest {
                _reportsUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun resetReports() {
        _reportsUIState.value = Resource.Success(Reports(emptyList()))
    }

    private fun getUserProfile() = viewModelScope.launch {
        try {
            homeUseCase.getUserInfo(auth.currentUser?.uid ?: return@launch).collectLatest {
                when (it) {
                    is Resource.Loading -> {  }
                    is Resource.Failure -> {  }
                    is Resource.Success -> {
                        getBookmark(it.data.bookmark ?: return@collectLatest)
                    }
                }
            }

        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }


    init {
        getSignedInUser()
        getUserProfile()
    }
}