package com.bitvolper.yogazzz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.model.AppThemePreference
import com.bitvolper.yogazzz.domain.model.FaqQuestion
import com.bitvolper.yogazzz.domain.model.History
import com.bitvolper.yogazzz.domain.model.NotificationPreference
import com.bitvolper.yogazzz.domain.model.Subscription
import com.bitvolper.yogazzz.domain.usecase.AppThemeUseCase
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.bitvolper.yogazzz.domain.usecase.PushNotificationUseCase
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val pushNotificationUseCase: PushNotificationUseCase,
    private val appThemeUseCase: AppThemeUseCase,
    private val homeUseCase: HomeUseCase) : ViewModel() {

        private companion object {
            const val TAG = "AccountViewModel"
        }

    private val auth = Firebase.auth

    private var _faqQuestionUIState = MutableStateFlow<Resource<FaqQuestion>>(Resource.Loading)
    val faqQuestionUIState: StateFlow<Resource<FaqQuestion>> get() = _faqQuestionUIState

    private var _subscriptionUIState = MutableStateFlow<Resource<Subscription>>(Resource.Loading)
    val subscriptionUIState: StateFlow<Resource<Subscription>> get() = _subscriptionUIState

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


    private var _accountInfoUIState = MutableStateFlow<AccountInfo>(AccountInfo())
    val accountInfoUIState: StateFlow<AccountInfo> get() = _accountInfoUIState

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

    fun getSubscription() = viewModelScope.launch {
        try {
            Timber.tag(TAG).d("View model called")
            homeUseCase.getSubscription().collectLatest {
                _subscriptionUIState.value = it

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

    fun updateFullName(value: String) {
        _accountInfoUIState.update {
            it.copy(fullName = value)
        }
    }

    fun updateGender(value: Int) {
        _accountInfoUIState.update {
            it.copy(gender = value)
        }
    }


    fun updateBirthdayDate(value: Long) {
        _accountInfoUIState.update {
            it.copy(birthdayDate = value)
        }
    }

    fun updateHeight(value: Int) {
        _accountInfoUIState.update {
            it.copy(height = value)
        }
    }

    fun updateCurrentWeight(value: Double) {
        _accountInfoUIState.update {
            it.copy(currentWeight = value)
        }
    }

    fun updateTargetWeight(value: Double) {
        _accountInfoUIState.update {
            it.copy(targetWeight = value)
        }
    }

    fun updateHistory(id: String) {

        val formatter = DateTimeFormatter.ofPattern("d-M-yyyy")
        val current = LocalDateTime.now().format(formatter)

        val history = mutableListOf<AccountInfo.HistoryData>().apply {
            addAll(_accountInfoUIState.value.history ?: emptyList())
            add(AccountInfo.HistoryData(id = id, date = current))
        }

        _accountInfoUIState.update {
            it.copy(history = history)
        }

        updateUserProfile()
    }

    fun updateUserProfile() = viewModelScope.launch {
        try {
            homeUseCase.updateUserInfo(auth.currentUser?.uid ?: return@launch, accountInfoUIState.value)
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getUserProfile() = viewModelScope.launch {
        try {
            homeUseCase.getUserInfo(auth.currentUser?.uid ?: return@launch).collectLatest {
                when (it) {
                    is Resource.Loading -> {  }
                    is Resource.Failure -> {  }
                    is Resource.Success -> {
                        _accountInfoUIState.value = it.data
                    }
                }
            }

            _accountInfoUIState.update {
                it.copy(email = auth.currentUser?.email ?: "")
            }

        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }


    init {
        getUserProfile()
    }
}