package com.bitvolper.yogazzz.domain.model

import java.io.Serializable


data class AccountInfo(
    val fullName: String? = null,
    val email: String? = null,
    val gender: Int? = null,
    val birthdayDate: Long? = null,
    val focusArea: Int? = null,
    val yogaGoal: List<Int?>? = null,
    val currentBodyShape: Int? = null,
    val desiredBodyShape: Int? = null,
    val experienceLevel: Int? = null,
    val sedentaryLifestyle: Boolean? = null,
    val plank: Int? = null,
    val legRaise: Int? = null,
    val yogaWeekDay: Int? = null,

    val height: Int? = null,
    val currentWeight: Double? = null,
    val targetWeight: Double? = null,

    val history: List<HistoryData>? = null,
    val bookmark: List<String>? = null,

    val reports: List<Reports>? = null

): Serializable {
    data class HistoryData(
        val id: String? = null,
        val date: String? = null
    ): Serializable

    data class Reports(
        val id: String? = null,
    )
}