package com.example.weatherapp.data.models

class WeatherDto(
    val weather: String,
    val temp: Int,
    val forecasts: List<ForecastDto>,
)