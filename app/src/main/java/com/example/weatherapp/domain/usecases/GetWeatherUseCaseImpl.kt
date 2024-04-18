package com.example.weatherapp.domain.usecases

import com.example.weatherapp.domain.WeatherAndCitiesRepository
import com.example.weatherapp.domain.mappers.WeatherMapper
import com.example.weatherapp.domain.models.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val repository: WeatherAndCitiesRepository,
    private val mapper: WeatherMapper,
) : GetWeatherUseCase {
    override suspend operator fun invoke(lat: Double, lon: Double): Flow<Weather> {
        return repository.getTemp(lat, lon).map(mapper::invoke)
    }
}