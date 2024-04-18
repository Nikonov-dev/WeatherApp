package com.example.weatherapp.cities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.domain.usecases.GetCitiesUseCase
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class CitiesViewModelFactory @Inject constructor(
    private val router: Router,
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitiesViewModelImpl::class.java)) {
            return CitiesViewModelImpl(router, getCitiesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}