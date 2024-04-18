package com.example.weatherapp.cities.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.cities.CitiesExceptionHandler
import com.example.weatherapp.domain.models.City
import com.example.weatherapp.domain.usecases.GetCitiesUseCase
import com.example.weatherapp.navigation.Screen
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModelImpl @Inject constructor(
    private val router: Router,
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel(), CitiesViewModel {
    companion object {
        private const val THROWABLE_TAG = "CITIES_VIEW_MODEL_THROWABLE"
    }

    override val progressStateFlow = MutableStateFlow(false)
    override val errorStateFlow = MutableStateFlow<String?>(null)
    override val citiesStateFlow = MutableStateFlow(listOf<City>())
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val exceptionHandler =
        CitiesExceptionHandler(
            CoroutineExceptionHandler,
            ::getCities,
            ::onError,
            THROWABLE_TAG
        )

    override fun onCityClicked(lat: Double, lon: Double) {
        router.navigateTo(Screen.weatherScreen(lat = lat, lon = lon))
    }

    override fun onViewCreated() {
        loadData()
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(exceptionHandler) {
            getCities()
        }
    }

    private suspend fun getCities() {
        progressStateFlow.value = true
        getCitiesUseCase()
            .flowOn(Dispatchers.IO)
            .collect { cities ->
                citiesStateFlow.value = cities.cities
                progressStateFlow.value = false
            }
    }

    private fun onError(errorMessage: String) {
        errorStateFlow.value = errorMessage
        progressStateFlow.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}