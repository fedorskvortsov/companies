package com.fedorskvortsov.companies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CompaniesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        const val DEBUG = true
    }
}