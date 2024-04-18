package com.example.weatherapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.cities.viewmodel.CitiesViewModel
import com.example.weatherapp.cities.viewmodel.CitiesViewModelFactory
import com.example.weatherapp.cities.viewmodel.CitiesViewModelImpl
import dagger.Binds
import dagger.Module

@Module
interface CitiesPresentationModule {
    @CitiesScope
    @Binds
    fun bindCitiesViewModelImpl(
        citiesViewModelImpl: CitiesViewModelImpl
    ): CitiesViewModel

    @CitiesScope
    @Binds
    fun bindCitiesViewModelFactory(
        citiesViewModelFactory: CitiesViewModelFactory
    ): ViewModelProvider.Factory
}