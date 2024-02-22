package com.bitvolper.yogazzz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.UserData
import com.bitvolper.yogazzz.domain.model.YogaCategoryWithRecommendation
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

    private var _discoverUIState = MutableStateFlow<Resource<PopularYogaWithFlexibility>>(Resource.Loading)
    val discoverUIState: StateFlow<Resource<PopularYogaWithFlexibility>> get() = _discoverUIState

    private var _yogaExerciseUIState = MutableStateFlow<Resource<YogaExercise>>(Resource.Loading)
    val yogaExerciseUIState: StateFlow<Resource<YogaExercise>> get() = _yogaExerciseUIState

    private var _bookmarkUIState = MutableStateFlow<Resource<YogaExercise>>(Resource.Loading)
    val bookmarkUIState: StateFlow<Resource<YogaExercise>> get() = _bookmarkUIState

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

    fun getPopularYoga() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getPopularYogaWithFlexibility().collectLatest {
                _discoverUIState.value = it
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

    fun getBookmarkYogaExercise() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getBookmarkYogaExercise().collectLatest {
                _bookmarkUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }


    fun updateBookmarkYogaExercise(bookmark: Boolean) = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.updateBookmarkYogaExercise(bookmark)
            getBookmarkYogaExercise()
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    init {
        getSignedInUser()
        getHomeContent()
        getPopularYoga()
    }
}