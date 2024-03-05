package com.bitvolper.yogazzz.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.bitvolper.yogazzz.utility.ACCOUNT_SETUP_MAX_SCREEN
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

data class AccountSetupUIState(
    val currentScreen: Int = 1
)
@HiltViewModel
class AccountSetupViewModel @Inject constructor(private val homeUseCase: HomeUseCase): ViewModel() {

    companion object {
        private const val TAG = "AccountSetupViewModel"
    }

    private val auth = Firebase.auth

    private var _uiState = MutableStateFlow<AccountSetupUIState>(AccountSetupUIState())
    val uiState: StateFlow<AccountSetupUIState> get() = _uiState

    private var _accountInfoUIState = MutableStateFlow<AccountInfo>(AccountInfo())
    val accountInfoUIState: StateFlow<AccountInfo> get() = _accountInfoUIState


    fun degradeCurrentScreen(navigateUp: () -> Unit = {  }) {
        if (uiState.value.currentScreen in 2..ACCOUNT_SETUP_MAX_SCREEN) {
            _uiState.update {
                it.copy(
                    currentScreen = it.currentScreen.dec()
                )
            }
            navigateUp()
        }
    }

    fun updateCurrentScreen() {
        if (uiState.value.currentScreen in 1..ACCOUNT_SETUP_MAX_SCREEN.dec()) {
            _uiState.update {
                it.copy(
                    currentScreen = it.currentScreen.inc()
                )
            }
        }
    }

    fun updateGender(gender: Int) {
        _accountInfoUIState.update {
            it.copy(gender = gender)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateFocusArea(focusArea: Int) {
        _accountInfoUIState.update {
            it.copy(focusArea = focusArea)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateYogaGoal(selectedIndex: List<Int>) {
        _accountInfoUIState.update {
            it.copy(yogaGoal = selectedIndex)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.yogaGoal.toString())
    }


    fun updateCurrentBodyShape(currentBodyShape: Int) {
        _accountInfoUIState.update {
            it.copy(currentBodyShape = currentBodyShape)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateDesiredBodyShape(desiredBodyShape: Int) {
        _accountInfoUIState.update {
            it.copy(desiredBodyShape = desiredBodyShape)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateExperienceLevel(experience: Int) {
        _accountInfoUIState.update {
            it.copy(experienceLevel = experience)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateSedentaryLifestyle(value: Boolean) {
        _accountInfoUIState.update {
            it.copy(sedentaryLifestyle = value)
        }

        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updatePlank(plank: Int) {
        _accountInfoUIState.update {
            it.copy(plank = plank)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateLegRaise(legRaise: Int) {
        _accountInfoUIState.update {
            it.copy(legRaise = legRaise)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateHeight(height: Int) {
        _accountInfoUIState.update {
            it.copy(height = height)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateCurrentWeight(currentWeight: Double) {
        _accountInfoUIState.update {
            it.copy(currentWeight = currentWeight)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateTargetWeight(targetWeight: Double) {
        _accountInfoUIState.update {
            it.copy(targetWeight = targetWeight)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateYogaWeekPlan(day: Int) {
        _accountInfoUIState.update {
            it.copy(yogaWeekDay = day)
        }
        Timber.tag(TAG).d(accountInfoUIState.value.toString())
    }

    fun updateUserProfile() = viewModelScope.launch {
        try {
            homeUseCase.updateUserInfo(auth.currentUser?.uid ?: return@launch, accountInfoUIState.value)
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }
}