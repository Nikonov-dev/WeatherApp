package com.example.weatherapp.data.network

class ForecastResponse(
    val date: String,
    val dayTemp: Int,
    val dayWeather: String,
    val nightTemp: Int,
    val nightWeather: String,
)