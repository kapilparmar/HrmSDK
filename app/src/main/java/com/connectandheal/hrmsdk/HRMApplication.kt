package com.connectandheal.hrmsdk

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

private const val TAG = "HRMApplication"

@HiltAndroidApp
class HRMApplication() : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    companion object {
        lateinit var instance: HRMApplication private set
        val applicationContext: Context
            get() {
                return instance.applicationContext
            }
    }

}
