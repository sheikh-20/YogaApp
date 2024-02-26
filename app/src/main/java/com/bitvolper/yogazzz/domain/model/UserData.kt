package com.bitvolper.yogazzz.domain.model

import com.google.firebase.auth.AdditionalUserInfo

data class UserData(
    val userId: String? = null,
    val userName: String? = null,
    val profilePictureUrl: String? = null,
    val email: String? = null,
    val additionalUserInfo: AdditionalUserInfo? = null
)
