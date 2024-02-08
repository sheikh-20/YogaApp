package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.AuthRepository
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SignUpEmailUseCase {
    operator fun invoke(email: String?, password: String?): Flow<Resource<AuthResult>>
}

class SignUpEmailInteractor @Inject constructor(private val repo: AuthRepository): SignUpEmailUseCase {
    override fun invoke(email: String?, password: String?): Flow<Resource<AuthResult>> {
        return repo.signIn(email = email, password = password)
    }
}