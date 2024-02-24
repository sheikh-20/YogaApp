package com.bitvolper.yogazzz.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.model.AppThemePreference
import com.bitvolper.yogazzz.domain.model.FaqQuestion
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import com.bitvolper.yogazzz.domain.usecase.AppThemeUseCase
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.bitvolper.yogazzz.domain.usecase.PushNotificationUseCase
import com.bitvolper.yogazzz.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val pushNotificationUseCase: PushNotificationUseCase,
    private val appThemeUseCase: AppThemeUseCase,
    private val homeUseCase: HomeUseCase) : ViewModel() {

        private companion object {
            const val TAG = "AccountViewModel"
        }

    private var _faqQuestionUIState = MutableStateFlow<Resource<FaqQuestion>>(Resource.Loading)
    val faqQuestionUIState: StateFlow<Resource<FaqQuestion>> get() = _faqQuestionUIState


    val isDailyReminderEnabled: Flow<NotificationPreference> = pushNotificationUseCase.readDailyReminder.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = NotificationPreference(true)
    )

    val isFeedbackAppUpdatesEnabled: Flow<NotificationPreference> = pushNotificationUseCase.readFeedbackAppUpdates.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = NotificationPreference(true)
    )


    val appThemeIndex: Flow<AppThemePreference> = appThemeUseCase.readAppThemePreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = AppThemePreference(0)
    )

    fun saveDailyReminderPreference(value: Boolean) = viewModelScope.launch {
        pushNotificationUseCase.saveDailyReminderPreference(value)
    }

    fun saveFeedbackAppUpdatePreference(value: Boolean) = viewModelScope.launch {
        pushNotificationUseCase.saveFeedbackAppUpdatePreference(value)
    }

    fun updateAppThemeIndex(value: Int) = viewModelScope.launch {
        appThemeUseCase.updateAppThemePreference(value)
    }


    fun getFaqQuestion() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getFaqQuestion().collectLatest {
                _faqQuestionUIState.value = it

                when (it) {
                    is Resource.Loading -> { }
                    is Resource.Failure -> { }
                    is Resource.Success -> {
                        Timber.tag(TAG).d(it.data.data.toString())
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    init {
        getFaqQuestion()
    }
}