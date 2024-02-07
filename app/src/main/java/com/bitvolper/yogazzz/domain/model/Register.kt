package com.bitvolper.yogazzz.domain.model

data class Register(
    val data: Data?
) {
    data class Data(
        val member: Member?,
        val token: String?
    ) {

        data class Member(
            val email: String?,
            val id: Int?,
            val profileSetupStatus: Boolean?,
            val role: String?
        )
    }
}