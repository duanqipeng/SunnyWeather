package com.example.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.model.PlaceResponse
import com.example.sunnyweather.logic.DataRepository
import com.example.sunnyweather.logic.dao.PlaceDao

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<PlaceResponse.Place>()

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    val placeLiveData = Transformations.switchMap(searchLiveData) {
        DataRepository.searchPlaces(it)
    }

    fun savePlace(place: PlaceResponse.Place) = DataRepository.savePlace(place)

    fun getSavedPlace() = DataRepository.getSavedPlace()

    fun isPlaceSaved() = DataRepository.isPlaceSaved()
}