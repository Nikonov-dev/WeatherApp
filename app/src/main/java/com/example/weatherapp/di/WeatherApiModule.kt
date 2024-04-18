package com.example.weatherapp.di

import com.example.weatherapp.data.network.WeatherResponse
import com.example.weatherapp.data.network.WeatherResponseDeserializer
import com.example.weatherapp.data.network.WeatherService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class WeatherApiModule {
    companion object {
        private const val BASE_URL = "https://api.weather.yandex.ru/v2/"
        const val API_KEY = "2ad6f46d-62d0-4b96-b0bf-516ac7aa7eec"
        const val COUNT_DAYS = 7
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    @WeatherApiQualifier
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(WeatherResponse::class.java, WeatherResponseDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherService(@WeatherApiQualifier retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WeatherApiQualifier