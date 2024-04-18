package com.example.weatherapp.di

import com.example.weatherapp.data.WeatherAndCitiesRepositoryImpl
import com.example.weatherapp.domain.WeatherAndCitiesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    fun bindRepositoryImpl(
        repositoryImpl: WeatherAndCitiesRepositoryImpl
    ): WeatherAndCitiesRepository
}