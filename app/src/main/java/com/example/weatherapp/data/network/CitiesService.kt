package com.example.weatherapp.data.network

import com.example.weatherapp.di.CitiesApiModule.Companion.COUNTRY
import com.example.weatherapp.di.CitiesApiModule.Companion.FEATURE_CLASS
import com.example.weatherapp.di.CitiesApiModule.Companion.MAX_ROWS
import com.example.weatherapp.di.CitiesApiModule.Companion.USERNAME
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesService {
    @GET("searchJSON")
    suspend fun getCities(
        @Query("country") country: String = COUNTRY,
        @Query("featureClass") featureClass: String = FEATURE_CLASS,
        @Query("maxRows") maxRows: Int = MAX_ROWS,
        @Query("username") username: String = USERNAME,
    ): CitiesResponse
}