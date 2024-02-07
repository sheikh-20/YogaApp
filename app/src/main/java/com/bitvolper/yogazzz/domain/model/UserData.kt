package com.bitvolper.yogazzz.domain.model

import com.google.firebase.auth.AdditionalUserInfo

data class UserData(
    val userId: String?,
    val userName: String?,
    val profilePictureUrl: String?,
    val email: String?,
    val additionalUserInfo: AdditionalUserInfo? = null
)
