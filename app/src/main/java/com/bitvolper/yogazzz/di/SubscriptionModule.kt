package com.bitvolper.yogazzz.di

import android.content.Context
import com.bitvolper.yogazzz.BuildConfig
import com.bitvolper.yogazzz.billing.BillingHelper
import com.bitvolper.yogazzz.billing.SUBSCRIPTION_ID
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.limurse.iap.IapConnector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubscriptionModule {

    @Provides
    @Singleton
    fun providesBillingHelper(@ApplicationContext context: Context, coroutineScope: CoroutineScope): BillingHelper {
        return BillingHelper(context, coroutineScope, arrayOf(), arrayOf(SUBSCRIPTION_ID))
    }

    @Provides
    @Singleton
    fun providesAkshatLibrary(@ApplicationContext context: Context):  IapConnector{
        return IapConnector(
            context = context,
            subscriptionKeys = listOf(SUBSCRIPTION_ID),
            key = BuildConfig.PUBLIC_API_KEY,
            enableLogging = true
        )
    }
}