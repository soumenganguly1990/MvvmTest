package com.soumen.mvvmtest

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by Soumen on 05-01-2018.
 */
class MvvmTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}