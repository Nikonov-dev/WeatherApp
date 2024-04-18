package com.example.weatherapp.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.domain.usecases.GetWeatherUseCase
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class WeatherViewModelFactory @Inject constructor(
    private val router: Router,
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModelImpl::class.java)) {
            return WeatherViewModelImpl(router, getWeatherUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}