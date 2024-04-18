package com.example.weatherapp.weather.viewmodel

import com.example.weatherapp.domain.models.Forecast
import kotlinx.coroutines.flow.StateFlow

interface WeatherViewModel {
    val refreshStateFlow: StateFlow<Boolean>
    val errorStateFlow: StateFlow<String?>
    val tempStateFlow: StateFlow<Int>
    val weatherStateFlow: StateFlow<String>
    val dayTempStateFlow: StateFlow<Int>
    val nightTempStateFlow: StateFlow<Int>
    val forecastStateFlow: StateFlow<List<Forecast>>
    fun onCitiesClicked()
    fun onViewCreated(lat: Double, lon: Double)
    fun onRefresh(lat: Double, lon: Double)
}