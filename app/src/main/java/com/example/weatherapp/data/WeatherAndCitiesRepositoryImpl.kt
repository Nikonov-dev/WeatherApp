package com.example.weatherapp.data

import com.example.weatherapp.data.mappers.CitiesMapper
import com.example.weatherapp.data.mappers.WeatherMapper
import com.example.weatherapp.data.models.CitiesDto
import com.example.weatherapp.data.models.WeatherDto
import com.example.weatherapp.data.network.CitiesService
import com.example.weatherapp.data.network.WeatherService
import com.example.weatherapp.domain.WeatherAndCitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class WeatherAndCitiesRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherMapper: WeatherMapper,
    private val citiesService: CitiesService,
    private val citiesMapper: CitiesMapper,
) : WeatherAndCitiesRepository {
    override suspend fun getTemp(lat: Double, lon: Double): Flow<WeatherDto> {
        val weather = weatherMapper(weatherService.getForecast(latitude = lat, longitude = lon))
        return flowOf(weather)
    }

    override suspend fun getCities(): Flow<CitiesDto> {
        val cities = citiesMapper(citiesService.getCities())
        return flowOf(cities)
    }
}