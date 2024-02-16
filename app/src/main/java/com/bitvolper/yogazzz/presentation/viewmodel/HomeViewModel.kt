package com.bitvolper.yogazzz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.bitvolper.yogazzz.domain.model.UserData
import com.bitvolper.yogazzz.domain.usecase.HomeUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val homeUseCase: HomeUseCase): ViewModel() {

    private companion object {
        const val TAG = "HomeViewModel"
    }

    private val auth = Firebase.auth

    private var _profileInfoUiState = MutableStateFlow<UserData>(UserData(userId = "", userName = "", profilePictureUrl = "", email = ""))
    val profileInfoUiState: StateFlow<UserData> = _profileInfoUiState


    private fun getSignedInUser() {
        auth.currentUser?.apply {
            _profileInfoUiState.value = UserData(
                userId = uid,
                userName = displayName,
                profilePictureUrl = photoUrl.toString(),
                email = email
            )
        }
    }

    private fun getYogaCategory() {
        try {
            homeUseCase.getYogaCategory()
        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception)
        }
    }

    init {
        getSignedInUser()
        getYogaCategory()
    }
}