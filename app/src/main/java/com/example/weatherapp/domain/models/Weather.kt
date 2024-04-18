package com.example.weatherapp.domain.models

class Weather(
    val weather: String,
    val temp: Int,
    val forecasts: List<Forecast>,
)