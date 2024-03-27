package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.PasswordResetRepository
import javax.inject.Inject

interface PasswordResetUseCase {
    suspend fun sendOtp(email: String)
}

class GetPasswordResetInteractors @Inject constructor(private val repository: PasswordResetRepository): PasswordResetUseCase {
    override suspend fun sendOtp(email: String) = repository.sendOtp(email)
}