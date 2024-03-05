package com.bitvolper.yogazzz.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.bitvolper.yogazzz.domain.model.AccountInfo
import com.bitvolper.yogazzz.utility.ACCOUNT_SETUP_MAX_SCREEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class AccountSetupUIState(
    val currentScreen: Int = 1
)
class AccountSetupViewModel: ViewModel() {

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
    }

    fun updateFocusArea(focusArea: Int) {
        _accountInfoUIState.update {
            it.copy(focusArea = focusArea)
        }
    }

    fun updateYogaGoal(selectedIndex: List<Int>) {
        _accountInfoUIState.update {
            it.copy(yogaGoal = selectedIndex)
        }
    }
}