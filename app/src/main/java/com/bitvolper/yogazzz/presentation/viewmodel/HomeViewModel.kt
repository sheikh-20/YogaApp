package com.bitvolper.yogazzz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.SerenityData
import com.bitvolper.yogazzz.domain.model.UserData
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
import com.bitvolper.yogazzz.domain.model.YogaData
import com.bitvolper.yogazzz.domain.model.YogaExercise
import com.bitvolper.yogazzz.domain.model.YogaRecommendation
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCase: HomeUseCase): ViewModel() {

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
            Timber.tag(TAG).d("View model called")
            homeUseCase.getYogaCategoryWithRecommendation().collectLatest {
                _homeUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getYogaExerciseByCategory(category: String = "improvedFlexibility") = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getYogaExerciseByCategory(category).collectLatest {
                _yogaCategoryUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getYogaRecommendation() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getRecommendation().collectLatest {
                _recommendationUIState.value = it
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
            Timber.tag(TAG).d("View model called")
            homeUseCase.getSerenityFlow(id).collectLatest {
                _serenityFlowUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getBookmark(id: List<String>) = viewModelScope.launch {
        try {
            homeUseCase.getBookmarkYogaExercise(id).collectLatest {
                _bookmarkUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getHistory(id: List<AccountInfo.HistoryData>) = viewModelScope.launch {
        try {
            homeUseCase.getHistory(id).collectLatest {
                _historyUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun resetHistory() {
        _historyUIState.value = Resource.Success(History(emptyList()))
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