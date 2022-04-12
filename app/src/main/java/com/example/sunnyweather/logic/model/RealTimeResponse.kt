package com.example.sunnyweather.logic.model


data class RealTimeResponse(val status: String, val result: Result) {

    data class Result(
        val temperature: Float,
        val skycon: String,
        val aqi: Float
    )
}
