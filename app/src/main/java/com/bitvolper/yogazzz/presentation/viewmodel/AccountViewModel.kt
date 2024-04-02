package com.bitvolper.yogazzz.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.bitvolper.yogazzz.presentation.home.account.millisecondToDate
import com.bitvolper.yogazzz.presentation.mybody.cmToFeet
import com.bitvolper.yogazzz.presentation.mybody.feetToCm
import com.bitvolper.yogazzz.presentation.mybody.kgToLb
import com.bitvolper.yogazzz.presentation.mybody.lbToKg
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

    private var _profilePhotoUIState = MutableStateFlow<Resource<Uri>>(Resource.Loading)
    val profilePhotoUIState get() = _profilePhotoUIState.asStateFlow()

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

    /**
     * height in cm
     * */
    var height by mutableIntStateOf(0)
        private set

    fun onHeight(value: Int) {
        height = value
    }

    fun updateHeight(value: Int) {
        _accountInfoUIState.update {
            it.copy(height = value, heightInFt = cmToFeet(value))
        }
    }


    /**
     * height in ft
     * */
    var heightInFt by mutableDoubleStateOf(0.0)
        private set

    fun onHeightFt(value: Double) {
        heightInFt = value
    }

    fun updateHeightFt(value: Double) {
        _accountInfoUIState.update {
            it.copy(heightInFt = value, height = feetToCm(value))
        }
    }


    /**
     * current weight in kg
     * */
    var currentWeight by mutableDoubleStateOf(0.0)
        private set

    fun onCurrentWeight(value: Double) {
        currentWeight = value
    }

    fun updateCurrentWeight(value: Double) {
        _accountInfoUIState.update {
            it.copy(currentWeight = value, currentWeightInLb = kgToLb(value))
        }
    }

    /**
     * current weight in lb
     * */
    var currentWeightInLb by mutableDoubleStateOf(0.0)
        private set

    fun onCurrentWeightLb(value: Double) {
        currentWeightInLb = value
    }

    fun updateCurrentWeightLb(value: Double) {
        _accountInfoUIState.update {
            it.copy(currentWeightInLb = value, currentWeight = lbToKg(value))
        }
    }


    /**
     * target weight in kg
     * */
    var targetWeight by mutableDoubleStateOf(0.0)
        private set

    fun onTargetWeight(value: Double) {
        targetWeight = value
    }

    fun updateTargetWeight(value: Double) {
        _accountInfoUIState.update {
            it.copy(targetWeight = value, targetWeightInLb = kgToLb(value))
        }
    }

    /**
     * target weight in lb
     * */
    var targetWeightInLb by mutableDoubleStateOf(0.0)
        private set

    fun onTargetWeightLb(value: Double) {
        targetWeightInLb = value
    }

    fun updateTargetWeightLb(value: Double) {
        _accountInfoUIState.update {
            it.copy(targetWeightInLb = value, targetWeight = lbToKg(value))
        }
    }


    fun onDeleteAccount() {
        _accountInfoUIState.update {
            it.copy(deleted = true)
        }
        updateUserProfile()
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

    fun updateReports(id: String) {
        val reports = mutableListOf<AccountInfo.Reports>().apply {
            addAll(_accountInfoUIState.value.reports ?: emptyList())
            add(AccountInfo.Reports(id = id))
        }

        _accountInfoUIState.update {
            it.copy(reports = reports)
        }
    }

    fun updateUserProfile() = viewModelScope.launch {
        try {
            homeUseCase.updateUserInfo(auth.currentUser?.uid ?: return@launch, accountInfoUIState.value)
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun updateBookmark(id: String) = viewModelScope.launch {

        val bookmark = mutableSetOf<String>().apply {
            addAll(_accountInfoUIState.value.bookmark ?: emptyList())
            add(id)
        }

        _accountInfoUIState.update {
            it.copy(bookmark = bookmark.toList())
        }

        updateUserProfile()
    }

    fun uploadProfilePhoto(uri: Uri) = viewModelScope.launch {
        try {
            homeUseCase.uploadProfilePhoto(auth.currentUser?.uid ?: return@launch, uri).collectLatest {
                when (it) {
                    is Resource.Loading -> {  }
                    is Resource.Failure -> {  }
                    is Resource.Success -> {
                        getProfilePhoto()
                    }
                }
            }
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    fun getProfilePhoto() = viewModelScope.launch {
        try {
            homeUseCase.getPhoto(auth.currentUser?.uid ?: return@launch).collectLatest {
                _profilePhotoUIState.value = it
            }
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
                it.copy(fullName = if (it.fullName.isNullOrEmpty()) "Avatar" else it.fullName, email = auth.currentUser?.email ?: "", createdDate = auth.currentUser?.metadata?.creationTimestamp?.millisecondToDate() ?: "")
            }

        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }


    init {
        getProfilePhoto()
        getUserProfile()
    }
}