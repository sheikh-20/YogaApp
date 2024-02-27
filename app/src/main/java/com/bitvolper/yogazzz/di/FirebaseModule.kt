package com.bitvolper.yogazzz.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun providesSignInClient(@ApplicationContext context: Context) = Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun providesFirebaseRealtimeDatabase() = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun providesFirebaseCloudDatastore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseCouldStorage() = Firebase.storage


    @Provides
    @Singleton
    fun providesApplicationContext(application: Application) = application.applicationContext

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
}