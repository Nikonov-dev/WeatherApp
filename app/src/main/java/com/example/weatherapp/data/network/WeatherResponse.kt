package com.example.weatherapp.data.network

class WeatherResponse(
    val weather: String,
    val temp: Int,
    val forecasts: List<ForecastResponse>,
)