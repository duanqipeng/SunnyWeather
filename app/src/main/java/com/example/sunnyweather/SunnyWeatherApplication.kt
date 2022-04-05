package com.example.sunnyweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {


    //daily "https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/101.6656,39.2072/daily?dailysteps=1"
    //realtime "https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/101.6656,39.2072/realtime"
    companion object {
        lateinit var appContext: Context
        const val MY_YOKEN = "I1pFCJWtssoqzqbq"
        const val TAG = "SunnyWeatherApplication"
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}