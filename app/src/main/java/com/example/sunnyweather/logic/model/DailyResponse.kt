package com.example.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyResponse(val status: String, val result: Result) {

    data class Result(val daily: Daily)

    data class Daily(
        val temperature: List<Temperature>,
        val skycon: List<Skyon>,
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>,
    )

    data class Temperature(val max: Float, val min: Float)
    data class Skyon(val value: String, val date: Date)
    data class LifeDescription(val desc: String)
}
