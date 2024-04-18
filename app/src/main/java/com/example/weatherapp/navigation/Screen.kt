package com.example.weatherapp.navigation

import com.example.weatherapp.cities.view.CitiesFragment
import com.example.weatherapp.weather.view.WeatherFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {
    private const val WEATHER_SCREEN_KEY = "screen_key:weather_screen"
    private const val CITIES_SCREEN_KEY = "screen_key:cities_screen"
    fun weatherScreen(lat: Double, lon: Double): FragmentScreen =
        FragmentScreen(key = WEATHER_SCREEN_KEY) {
            WeatherFragment.getNewInstance(lat, lon)
        }

    fun citiesScreen(): FragmentScreen =
        FragmentScreen(key = CITIES_SCREEN_KEY) {
            CitiesFragment()
        }
}

