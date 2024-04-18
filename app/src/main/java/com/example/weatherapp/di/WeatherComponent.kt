package com.example.weatherapp.di

import com.example.weatherapp.weather.view.WeatherFragment
import dagger.Component
import javax.inject.Scope

@WeatherScope
@Component(
    modules = [WeatherPresentationModule::class, WeatherDomainModule::class, RepositoryModule::class],
    dependencies = [NavigationDeps::class, NetworkDeps::class]
)
interface WeatherComponent {
    fun inject(weatherFragment: WeatherFragment)

    @Component.Builder
    interface Builder {
        fun navigationDeps(navigationDeps: NavigationDeps): Builder
        fun networkDeps(networkDeps: NetworkDeps): Builder
        fun build(): WeatherComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class WeatherScope