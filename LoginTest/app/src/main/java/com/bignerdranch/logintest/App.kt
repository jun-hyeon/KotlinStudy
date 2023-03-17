package com.bignerdranch.logintest

import android.app.Application

class App : Application() {

    companion object{
        lateinit var prefs: PreferenceUtil
        lateinit var realPath : RealPath
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        realPath = RealPath()
        super.onCreate()
    }
}