package com.example.weatherapp.domain.usecases

import com.example.weatherapp.domain.models.Weather
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    suspend operator fun invoke(lat: Double, lon: Double): Flow<Weather>
}