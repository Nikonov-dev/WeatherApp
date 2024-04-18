package com.example.weatherapp.domain.mappers

import com.example.weatherapp.data.models.ForecastDto
import com.example.weatherapp.data.models.WeatherDto
import com.example.weatherapp.domain.models.Forecast
import com.example.weatherapp.domain.models.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    operator fun invoke(weatherDto: WeatherDto): Weather {
        val forecasts = weatherDto.forecasts.map(::mapForecastDtoToDomain)
        return with(weatherDto) {
            Weather(
                weather = weather,
                temp = temp,
                forecasts = forecasts
            )
        }
    }

    private fun mapForecastDtoToDomain(forecastDto: ForecastDto): Forecast {
        return with(forecastDto) {
            Forecast(
                date = date,
                dayTemp = dayTemp,
                dayWeather = dayWeather,
                nightTemp = nightTemp,
                nightWeather = nightWeather
            )
        }
    }
}