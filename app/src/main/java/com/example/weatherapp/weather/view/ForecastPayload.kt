package com.example.weatherapp.weather.view

class ForecastPayload(
    var date: String? = null,
    var dayTemp: Int? = null,
    var dayWeather: String? = null,
    var nightTemp: Int? = null,
    var nightWeather: String? = null,
)