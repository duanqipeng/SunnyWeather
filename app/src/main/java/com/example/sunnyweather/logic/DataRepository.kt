package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.dao.PlaceDao
import com.example.sunnyweather.logic.model.PlaceResponse
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

object DataRepository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("Response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<PlaceResponse.Place>>(e)
        }
        emit(result)
    }

    fun refeeshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealTime = async { SunnyWeatherNetwork.getRealTimeWeather(lng, lat) }
                val deferredDaily = async { SunnyWeatherNetwork.getDailyWeather(lng, lat) }
                val realTimeResponse = deferredRealTime.await()
                val dailyResponse = deferredDaily.await()
                if (realTimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    Result.success(
                        Weather(
                            realTimeResponse.result,
                            dailyResponse.result.daily
                        )
                    )
                } else {
                    Result.failure(
                        java.lang.RuntimeException(
                            "realtimeResponse status is ${realTimeResponse.status},dailyResponse status is ${dailyResponse.status}"
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Result.failure<Weather>(e)
        }
        emit(result)
    }

    fun savePlace(place: PlaceResponse.Place) = PlaceDao.savaPlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}