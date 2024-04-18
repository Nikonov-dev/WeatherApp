package com.example.weatherapp.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule(val cicerone: Cicerone<Router>) {
    @Singleton
    @Provides
    fun provideRouter(): Router {
        return cicerone.router
    }
}