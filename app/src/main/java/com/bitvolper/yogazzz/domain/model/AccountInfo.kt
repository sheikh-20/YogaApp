package com.bitvolper.yogazzz.domain.model

data class AccountInfo(
    val fullName: String? = null,
    val email: String? = null,
    val gender: Int? = null,
    val birthdayDate: Long? = null,
    val focusArea: Int? = null,

    val height: Int? = null,
    val currentWeight: Double? = null,
    val targetWeight: Double? = null,

    val history: List<String>? = null
)