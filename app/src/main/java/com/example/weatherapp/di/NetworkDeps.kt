package com.example.weatherapp.di

import com.example.weatherapp.data.network.CitiesService
import com.example.weatherapp.data.network.WeatherService

interface NetworkDeps {
    fun weatherService(): WeatherService
    fun citiesService(): CitiesService
}