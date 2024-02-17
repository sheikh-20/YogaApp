package com.bitvolper.yogazzz.di

import com.bitvolper.yogazzz.data.repository.AuthRepository
import com.bitvolper.yogazzz.data.repository.GetNotificationPreferenceImpl
import com.bitvolper.yogazzz.data.repository.GoogleRepositoryImpl
import com.bitvolper.yogazzz.data.repository.HomeRepository
import com.bitvolper.yogazzz.data.repository.HomeRepositoryImpl
import com.bitvolper.yogazzz.data.repository.NotificationPreferenceRepository
import com.bitvolper.yogazzz.data.repository.SignInEmailRepositoryImpl
import com.bitvolper.yogazzz.data.repository.SignUpEmailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //Google
    @Binds
    @Named("GoogleRepo")
    abstract fun providesGoogleRepoImpl(googleRepoImpl: GoogleRepositoryImpl): AuthRepository


    //Email
    @Binds
    @Named("SignInEmailRepo")
    abstract fun providesSignInEmailRepoImpl(signInEmailRepoImpl: SignInEmailRepositoryImpl): AuthRepository

    @Binds
    @Named("SignUpEmailRepo")
    abstract fun providesSignUpEmailRepoImpl(signUpEmailRepoImpl: SignUpEmailRepositoryImpl): AuthRepository

    @Binds
    abstract fun providesHomeRepoImpl(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun providesNotificationPreferenceImpl(notificationPreferenceImpl: GetNotificationPreferenceImpl): NotificationPreferenceRepository

}