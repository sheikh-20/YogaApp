package com.bitvolper.yogazzz

import android.app.Application
import timber.log.Timber

class YogaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}