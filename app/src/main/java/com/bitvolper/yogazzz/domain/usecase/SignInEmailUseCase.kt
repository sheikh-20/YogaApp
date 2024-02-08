package com.bitvolper.yogazzz.domain.usecase

import com.bitvolper.yogazzz.data.repository.AuthRepository
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SignInEmailUseCase {
    operator fun invoke(email: String?, password: String?): Flow<Resource<AuthResult>>
}

class SignInEmailInteractor @Inject constructor(private val repo: AuthRepository): SignInEmailUseCase {

    private companion object {
        const val TAG = "SignInEmailInteractor"
    }

    override fun invoke(email: String?, password: String?): Flow<Resource<AuthResult>> {
        return repo.signIn(email = email, password = password)
    }
}