package com.example.weatherapp.di

import com.example.weatherapp.cities.view.CitiesFragment
import dagger.Component
import javax.inject.Scope

@CitiesScope
@Component(
    modules = [CitiesPresentationModule::class, CitiesDomainModule::class, RepositoryModule::class],
    dependencies = [NavigationDeps::class, NetworkDeps::class]
)
interface CitiesComponent {
    fun inject(citiesFragment: CitiesFragment)

    @Component.Builder
    interface Builder {
        fun navigationDeps(navigationDeps: NavigationDeps): Builder
        fun networkDeps(networkDeps: NetworkDeps): Builder
        fun build(): CitiesComponent
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CitiesScope