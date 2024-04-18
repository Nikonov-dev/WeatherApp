package com.example.weatherapp.di

import com.example.weatherapp.data.network.CitiesService
import com.example.weatherapp.data.network.WeatherService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherApiModule::class, CitiesApiModule::class])
interface NetworkComponent : NetworkDeps {
    override fun weatherService(): WeatherService
    override fun citiesService(): CitiesService
}