package com.example.weatherapp.di

import com.example.weatherapp.domain.usecases.GetCitiesUseCase
import com.example.weatherapp.domain.usecases.GetCitiesUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface CitiesDomainModule {
    @CitiesScope
    @Binds
    fun bindGetCitiesUseCaseImpl(
        getCitiesUseCaseImpl: GetCitiesUseCaseImpl
    ): GetCitiesUseCase
}