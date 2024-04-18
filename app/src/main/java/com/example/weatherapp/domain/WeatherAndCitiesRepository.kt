package com.example.weatherapp.domain

import com.example.weatherapp.data.models.CitiesDto
import com.example.weatherapp.data.models.WeatherDto
import kotlinx.coroutines.flow.Flow

interface WeatherAndCitiesRepository {
    suspend fun getTemp(lat: Double, lon: Double): Flow<WeatherDto>
    suspend fun getCities(): Flow<CitiesDto>
}