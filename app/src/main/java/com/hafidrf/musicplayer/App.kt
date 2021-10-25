package com.hafidrf.musicplayer

import android.app.Application
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.hafidrf.musicplayer.di.myAppModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(myAppModule)
        }
    }

}