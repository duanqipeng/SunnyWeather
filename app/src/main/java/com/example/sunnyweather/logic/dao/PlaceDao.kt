package com.example.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.PlaceResponse
import com.google.gson.Gson

object PlaceDao {
    fun savaPlace(place: PlaceResponse.Place) {
        getSharepreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): PlaceResponse.Place {
        val savedJson = getSharepreferences().getString("place", "")
        return Gson().fromJson(savedJson, PlaceResponse.Place::class.java)
    }

    fun isPlaceSaved() = getSharepreferences().contains("place")

    private fun getSharepreferences() =
        SunnyWeatherApplication.appContext.getSharedPreferences(
            "sunny_weather",
            Context.MODE_PRIVATE
        )
}