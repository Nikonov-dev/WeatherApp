package com.example.weatherapp

import com.example.weatherapp.di.DaggerCitiesComponent
import com.example.weatherapp.di.DaggerNavigationComponent
import com.example.weatherapp.di.DaggerNetworkComponent
import com.example.weatherapp.di.DaggerWeatherComponent
import com.example.weatherapp.di.NavigationComponent
import com.example.weatherapp.di.NavigationModule
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class ComponentHolder {
    private lateinit var navigationComponent: NavigationComponent
    private val networkComponent by lazy {
        DaggerNetworkComponent.create()
    }
    val weatherComponent by lazy {
        DaggerWeatherComponent.builder()
            .navigationDeps(navigationComponent)
            .networkDeps(networkComponent)
            .build()
    }
    val citiesComponent by lazy {
        DaggerCitiesComponent.builder()
            .navigationDeps(navigationComponent)
            .networkDeps(networkComponent)
            .build()
    }

    fun initNavigationComponent(cicerone: Cicerone<Router>) {
        navigationComponent = DaggerNavigationComponent.builder()
            .navigationModule(NavigationModule(cicerone))
            .build()
    }
}