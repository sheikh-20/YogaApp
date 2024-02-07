package com.bitvolper.yogazzz.data.repository

import android.app.Activity
import android.content.Context
import com.bitvolper.yogazzz.utility.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

interface AuthRepository {
    fun signIn(activity: Activity? = null, token: String? = null, email: String? = null, password: String? = null): Flow<Resource<AuthResult>>
}

class GoogleRepositoryImpl @Inject constructor(private val context: Context,
                                               private val auth: FirebaseAuth
): AuthRepository {
    private companion object {
        const val TAG = "GoogleRepoImpl"
    }

    override fun signIn(activity: Activity?, token: String?, email: String?, password: String?): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)

        val result = auth.signInWithCredential(GoogleAuthProvider.getCredential(token ?: throw Throwable(), null)).await()

        Timber.tag(TAG).d(result.additionalUserInfo.toString())
        emit(Resource.Success(result))
    }.catch {
        Timber.tag(TAG).e(it)
        it.printStackTrace()
        emit(Resource.Failure(it))
    }

}
