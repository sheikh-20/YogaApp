package com.bitvolper.yogazzz.presentation.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.usecase.SignInEmailUseCase
import com.bitvolper.yogazzz.domain.usecase.SignInGoogleUseCase
import com.bitvolper.yogazzz.domain.usecase.SignUpEmailUseCase
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class OnboardUIState(val isEmailError: Boolean = false, val isPasswordError: Boolean = false)
@HiltViewModel
class OnboardingViewModel @Inject constructor(private val signInGoogleUseCase: SignInGoogleUseCase,
                                              private val signInEmailUseCase: SignInEmailUseCase,
                                              private val signUpEmailUseCase: SignUpEmailUseCase): ViewModel() {

    private companion object {
        const val TAG = "OnboardingViewModel"
    }

    private val auth = Firebase.auth

    private var _loading = MutableStateFlow<Boolean>(true)
    val loading: StateFlow<Boolean> get() = _loading

    private var _socialSignIn = MutableSharedFlow<Resource<AuthResult>>()
    val socialSignIn get() = _socialSignIn.asSharedFlow()


    private var _loginUIState = MutableStateFlow(OnboardUIState())
    val loginUIState: StateFlow<OnboardUIState> get() = _loginUIState

    private var _signupUIState = MutableStateFlow(OnboardUIState())
    val signupUIState: StateFlow<OnboardUIState> get() = _signupUIState

    var email by mutableStateOf("")
        private set

    fun onEmailChange(value: String) {
        email = value
    }


    fun getUserInfo() = auth.currentUser

    fun signInGoogle(activity: Activity?, intent: Intent?) = viewModelScope.launch {
        signInGoogleUseCase(activity, intent).collectLatest {
            Timber.tag(TAG).d("Google called")
            _socialSignIn.emit(it)
        }
    }


    fun signInEmail(email: String?, password: String?) = viewModelScope.launch {
        if (email.isNullOrEmpty()) {
            _loginUIState.update {
                it.copy(isEmailError = true)
            }
        } else if (email.contains("@").not() || email.contains(".com").not()) {
            _loginUIState.update {
                it.copy(isEmailError = true)
            }
        } else if (password.isNullOrEmpty()){
            _loginUIState.update {
                it.copy(isPasswordError = true)
            }
        } else {
            _loginUIState.update {
                it.copy(isEmailError = false, isPasswordError = false)
            }
            signInEmailUseCase(email = email, password = password).collectLatest {
                Timber.tag(TAG).d("Email called")
                _socialSignIn.emit(it)
            }
        }
    }

    fun signUpEmail(email: String?, password: String?) = viewModelScope.launch {
        if (email.isNullOrEmpty()) {
            _signupUIState.update {
                it.copy(isEmailError = true)
            }
        } else if (email.contains("@").not() || email.contains(".com").not()) {
            _signupUIState.update {
                it.copy(isEmailError = true)
            }
        } else if (password.isNullOrEmpty()){
            _signupUIState.update {
                it.copy(isPasswordError = true)
            }
        } else {
            signUpEmailUseCase(email = email, password = password).collectLatest {
                Timber.tag(TAG).d("Email called")
                _socialSignIn.emit(it)
            }
        }
    }


    fun signOut() = viewModelScope.launch {
        auth.signOut()
    }

    init {
        viewModelScope.launch {
            delay(3_000L)
            _loading.value = false
        }
    }
}