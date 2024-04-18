package com.example.weatherapp.data.network

import com.example.weatherapp.di.WeatherApiModule.Companion.API_KEY
import com.example.weatherapp.di.WeatherApiModule.Companion.COUNT_DAYS
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast")
    suspend fun getForecast(
        @Header("X-Yandex-API-Key") apiKey: String = API_KEY,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") days: Int = COUNT_DAYS,
        @Query("hours") hours: Boolean = false,
    ): WeatherResponse
}