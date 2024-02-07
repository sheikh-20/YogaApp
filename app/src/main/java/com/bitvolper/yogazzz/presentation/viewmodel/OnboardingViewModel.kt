package com.bitvolper.yogazzz.presentation.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitvolper.yogazzz.domain.usecase.SignInGoogleUseCase
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
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val signInGoogleUseCase: SignInGoogleUseCase): ViewModel() {

    private companion object {
        const val TAG = "OnboardingViewModel"
    }

    private val auth = Firebase.auth

    private var _loading = MutableStateFlow<Boolean>(true)
    val loading: StateFlow<Boolean> get() = _loading

    private var _socialSignIn = MutableSharedFlow<Resource<AuthResult>>()
    val socialSignIn get() = _socialSignIn.asSharedFlow()

    fun getUserInfo() = auth.currentUser

    fun signInGoogle(activity: Activity?, intent: Intent?) = viewModelScope.launch {
        signInGoogleUseCase(activity, intent).collectLatest {
            Timber.tag(TAG).d("Google called")
            _socialSignIn.emit(it)
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