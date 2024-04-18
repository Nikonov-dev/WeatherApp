package com.example.weatherapp.di

import com.github.terrakok.cicerone.Router
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NavigationModule::class]
)
interface NavigationComponent : NavigationDeps {
    override fun router(): Router

    @Component.Builder
    interface Builder {
        fun navigationModule(navigationModule: NavigationModule): Builder
        fun build(): NavigationComponent
    }
}