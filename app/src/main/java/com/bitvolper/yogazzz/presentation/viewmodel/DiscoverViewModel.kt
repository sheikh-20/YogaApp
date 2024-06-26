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
import com.bitvolper.yogazzz.domain.model.AppLanguagePreference
import com.bitvolper.yogazzz.domain.model.FlexibilityStrength
import com.bitvolper.yogazzz.domain.model.Meditation
import com.bitvolper.yogazzz.domain.model.PopularYoga
import com.bitvolper.yogazzz.domain.model.PopularYogaWithFlexibility
import com.bitvolper.yogazzz.domain.model.StressRelief
import com.bitvolper.yogazzz.domain.model.YogaData
import com.bitvolper.yogazzz.domain.usecase.AppLanguageUseCase
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.bitvolper.yogazzz.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DiscoverViewModel @Inject constructor(
                                private val homeUseCase: HomeUseCase,
                                val player: Player,
                                private val appLanguageUseCase: AppLanguageUseCase
): ViewModel() {

    private companion object {
        const val TAG = "DiscoverViewModel"
    }


    private var _popularYoga = MutableStateFlow<Resource<YogaData>>(Resource.Loading)
    val popularYoga: StateFlow<Resource<YogaData>> get() = _popularYoga

    private var _discoverUIState = MutableStateFlow<Resource<PopularYogaWithFlexibility>>(Resource.Loading)
    val discoverUIState: StateFlow<Resource<PopularYogaWithFlexibility>> get() = _discoverUIState


    private var _adjustYogaLevel = MutableStateFlow<Resource<YogaData>>(Resource.Loading)
    val adjustYogaLevel: StateFlow<Resource<YogaData>> get() = _adjustYogaLevel

    private var _flexibilityStrength = MutableStateFlow<Resource<YogaData>>(Resource.Loading)
    val flexibilityStrength: StateFlow<Resource<YogaData>> get() = _flexibilityStrength

    private var _stressRelief = MutableStateFlow<Resource<YogaData>>(Resource.Loading)
    val stressRelief: StateFlow<Resource<YogaData>> get() = _stressRelief


    private var _meditationUIState = MutableStateFlow<Resource<Meditation>>(Resource.Loading)
    val meditationUIState: StateFlow<Resource<Meditation>> get() = _meditationUIState

    var meditation by mutableStateOf(Meditation.Data())
        private set


    val appLanguageIndex: Flow<AppLanguagePreference> = appLanguageUseCase.readLanguagePreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = AppLanguagePreference(0)
    )

    fun getExploreContent() = viewModelScope.launch {

        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getPopularYogaWithFlexibility("en").collectLatest { data ->
                            _discoverUIState.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getPopularYogaWithFlexibility("es").collectLatest { data ->
                            _discoverUIState.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getPopularYoga() = viewModelScope.launch {

        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getPopularYoga("en").collectLatest { data ->
                            _popularYoga.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getPopularYoga("es").collectLatest { data ->
                            _popularYoga.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getAdjustYogaLevel() = viewModelScope.launch {
        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getAdjustYogaLevel("en").collectLatest { data ->
                            _adjustYogaLevel.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getAdjustYogaLevel("es").collectLatest { data ->
                            _adjustYogaLevel.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getFlexibilityStrength() = viewModelScope.launch {

        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getFlexibilityStrength("en").collectLatest { data ->
                            _flexibilityStrength.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getFlexibilityStrength("es").collectLatest { data ->
                            _flexibilityStrength.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getStressRelief() = viewModelScope.launch {

        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getStressRelief("en").collectLatest { data ->
                            _stressRelief.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getStressRelief("es").collectLatest { data ->
                            _stressRelief.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getMeditation() = viewModelScope.launch {

        try {
            appLanguageIndex.collectLatest {
                when (it.language) {
                    0 -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getMeditation("en").collectLatest { data ->
                            _meditationUIState.value = data
                        }
                    }
                    else  -> {
                        Timber.tag(TAG).d("View model called")
                        homeUseCase.getMeditation("es").collectLatest { data ->
                            _meditationUIState.value = data
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun updateMeditation(value: Meditation.Data) {
        meditation = value
    }

}