package com.bitvolper.yogazzz.di

import com.bitvolper.yogazzz.data.repository.AuthRepository
import com.bitvolper.yogazzz.domain.usecase.SignInEmailInteractor
import com.bitvolper.yogazzz.domain.usecase.SignInEmailUseCase
import com.bitvolper.yogazzz.domain.usecase.SignInGoogleInteractor
import com.bitvolper.yogazzz.domain.usecase.SignInGoogleUseCase
import com.bitvolper.yogazzz.domain.usecase.SignUpEmailInteractor
import com.bitvolper.yogazzz.domain.usecase.SignUpEmailUseCase
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun providesSignInGoogleUseCase(@Named("GoogleRepo") authRepository: AuthRepository, oneTapClient: SignInClient): SignInGoogleUseCase {
        return SignInGoogleInteractor(authRepository, oneTapClient)
    }

    @Provides
    @Singleton
    fun providesSignInEmailUseCase(@Named("SignInEmailRepo") authRepository: AuthRepository): SignInEmailUseCase {
        return SignInEmailInteractor(authRepository)
    }

    @Provides
    @Singleton
    fun providesSignUpEmailUseCase(@Named("SignUpEmailRepo") authRepository: AuthRepository): SignUpEmailUseCase {
        return SignUpEmailInteractor(authRepository)
    }
}
