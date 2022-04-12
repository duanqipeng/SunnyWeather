package com.example.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.model.PlaceResponse
import com.example.sunnyweather.logic.DataRepository

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<PlaceResponse.Location>()
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = PlaceResponse.Location(lng, lat)
    }

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        DataRepository.refeeshWeather(it.lng, it.lat)
    }
}