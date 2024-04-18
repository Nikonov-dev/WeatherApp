package com.example.weatherapp.di

import com.example.weatherapp.data.network.CitiesResponse
import com.example.weatherapp.data.network.CitiesResponseDeserializer
import com.example.weatherapp.data.network.CitiesService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class CitiesApiModule {
    companion object {
        private const val BASE_URL = "http://api.geonames.org/"
        const val USERNAME = "weatherapp123122"
        const val MAX_ROWS = 1000
        const val FEATURE_CLASS = "P"
        const val COUNTRY = "RU"
    }

    @Singleton
    @Provides
    @CitiesApiQualifier
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(CitiesResponse::class.java, CitiesResponseDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideCitiesService(@CitiesApiQualifier retrofit: Retrofit): CitiesService {
        return retrofit.create(CitiesService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CitiesApiQualifier