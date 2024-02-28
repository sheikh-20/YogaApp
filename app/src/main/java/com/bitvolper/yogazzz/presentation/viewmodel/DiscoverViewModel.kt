package com.bitvolper.yogazzz.presentation.viewmodel

import android.content.Context
import android.media.browse.MediaBrowser
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.bitvolper.yogazzz.domain.model.AdjustYogaLevel
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.domain.model.Meditation
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.StressRelief
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.bitvolper.yogazzz.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DiscoverViewModel @Inject constructor(private val homeUseCase: HomeUseCase, val player: Player): ViewModel() {

    private companion object {
        const val TAG = "DiscoverViewModel"
    }


    private var _popularYoga = MutableStateFlow<Resource<PopularYoga>>(Resource.Loading)
    val popularYoga: StateFlow<Resource<PopularYoga>> get() = _popularYoga

    private var _discoverUIState = MutableStateFlow<Resource<PopularYogaWithFlexibility>>(Resource.Loading)
    val discoverUIState: StateFlow<Resource<PopularYogaWithFlexibility>> get() = _discoverUIState


    private var _adjustYogaLevel = MutableStateFlow<Resource<AdjustYogaLevel>>(Resource.Loading)
    val adjustYogaLevel: StateFlow<Resource<AdjustYogaLevel>> get() = _adjustYogaLevel

    private var _flexibilityStrength = MutableStateFlow<Resource<FlexibilityStrength>>(Resource.Loading)
    val flexibilityStrength: StateFlow<Resource<FlexibilityStrength>> get() = _flexibilityStrength

    private var _stressRelief = MutableStateFlow<Resource<StressRelief>>(Resource.Loading)
    val stressRelief: StateFlow<Resource<StressRelief>> get() = _stressRelief


    private var _meditationUIState = MutableStateFlow<Resource<Meditation>>(Resource.Loading)
    val meditationUIState: StateFlow<Resource<Meditation>> get() = _meditationUIState

    var meditation by mutableStateOf(Meditation.Data())
        private set

    fun getExploreContent() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getPopularYogaWithFlexibility().collectLatest {
                _discoverUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getPopularYoga() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getPopularYoga().collectLatest {
                _popularYoga.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getAdjustYogaLevel() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getAdjustYogaLevel().collectLatest {
                _adjustYogaLevel.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getFlexibilityStrength() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getFlexibilityStrength().collectLatest {
                _flexibilityStrength.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getStressRelief() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getStressRelief().collectLatest {
                _stressRelief.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getMeditation() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getMeditation().collectLatest {
                _meditationUIState.value = it
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun updateMeditation(value: Meditation.Data) {
        meditation = value
    }

}