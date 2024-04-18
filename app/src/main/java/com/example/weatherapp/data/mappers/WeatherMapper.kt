package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.models.ForecastDto
import com.example.weatherapp.data.models.WeatherDto
import com.example.weatherapp.data.network.ForecastResponse
import com.example.weatherapp.data.network.WeatherResponse
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    operator fun invoke(weatherResponse: WeatherResponse): WeatherDto {
        val forecastsDto = weatherResponse.forecasts.map(::mapForecastResponseToDto)
        return with(weatherResponse) {
            WeatherDto(
                weather = weather,
                temp = temp,
                forecasts = forecastsDto
            )
        }
    }

    private fun mapForecastResponseToDto(forecastResponse: ForecastResponse): ForecastDto {
        return with(forecastResponse) {
            ForecastDto(
                date = date,
                dayTemp = dayTemp,
                dayWeather = dayWeather,
                nightTemp = nightTemp,
                nightWeather = nightWeather
            )
        }
    }
}