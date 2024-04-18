package com.example.weatherapp.di

import com.example.weatherapp.domain.usecases.GetWeatherUseCase
import com.example.weatherapp.domain.usecases.GetWeatherUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface WeatherDomainModule {
    @WeatherScope
    @Binds
    fun bindGetWeatherUseCase(
        getTempUseCaseImpl: GetWeatherUseCaseImpl
    ): GetWeatherUseCase
}