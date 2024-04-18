package com.example.weatherapp.cities.viewmodel

import com.example.weatherapp.domain.models.City
import kotlinx.coroutines.flow.StateFlow

interface CitiesViewModel {
    val progressStateFlow: StateFlow<Boolean>
    val errorStateFlow: StateFlow<String?>
    val citiesStateFlow: StateFlow<List<City>>
    fun onCityClicked(lat: Double, lon: Double)
    fun onViewCreated()
    fun onRefresh()
}