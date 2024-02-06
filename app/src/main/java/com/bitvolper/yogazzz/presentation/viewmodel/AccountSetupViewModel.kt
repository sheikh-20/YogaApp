package com.bitvolper.yogazzz.presentation.viewmodel


import androidx.lifecycle.ViewModel
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
}