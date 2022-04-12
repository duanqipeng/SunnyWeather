package com.example.sunnyweather.logic.model

data class Weather(val realtime: RealTimeResponse.Result, val daily: DailyResponse.Daily)
