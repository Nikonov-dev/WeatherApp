package com.example.weatherapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.weather.viewmodel.WeatherViewModelFactory
import com.example.weatherapp.weather.viewmodel.WeatherViewModel
import com.example.weatherapp.weather.viewmodel.WeatherViewModelImpl
import dagger.Binds
import dagger.Module

@Module
interface WeatherPresentationModule {
    @WeatherScope
    @Binds
    fun bindWeatherViewModelImpl(
        weatherViewModelImpl: WeatherViewModelImpl
    ): WeatherViewModel

    @WeatherScope
    @Binds
    fun bindWeatherViewModelFactory(
        weatherViewModelFactory: WeatherViewModelFactory
    ): ViewModelProvider.Factory
}