package com.example.weatherapp.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.Forecast
import com.example.weatherapp.domain.usecases.GetWeatherUseCase
import com.example.weatherapp.navigation.Screen
import com.example.weatherapp.weather.WeatherExceptionHandler
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModelImpl @Inject constructor(
    private val router: Router,
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel(), WeatherViewModel {
    companion object {
        private const val THROWABLE_TAG = "WEATHER_VIEW_MODEL_THROWABLE"
    }

    override val refreshStateFlow = MutableStateFlow(false)
    override val errorStateFlow = MutableStateFlow<String?>(null)
    override val tempStateFlow = MutableStateFlow(0)
    override val weatherStateFlow = MutableStateFlow("")
    override val dayTempStateFlow = MutableStateFlow(0)
    override val nightTempStateFlow = MutableStateFlow(0)
    override val forecastStateFlow = MutableStateFlow(listOf<Forecast>())
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val exceptionHandler =
        WeatherExceptionHandler(
            CoroutineExceptionHandler,
            ::getWeather,
            ::onError,
            THROWABLE_TAG
        )

    override fun onCitiesClicked() {
        router.navigateTo(Screen.citiesScreen())
    }

    override fun onViewCreated(lat: Double, lon: Double) {
        loadData(lat = lat, lon = lon)
    }

    override fun onRefresh(lat: Double, lon: Double) {
        loadData(lat = lat, lon = lon)
    }

    private fun loadData(lat: Double, lon: Double) {
        viewModelScope.launch(exceptionHandler) {
            getWeather(lat, lon)
        }
    }

    private suspend fun getWeather(lat: Double, lon: Double) {
        refreshStateFlow.value = true
        getWeatherUseCase(lat, lon)
            .flowOn(Dispatchers.IO)
            .collect { weather ->
                tempStateFlow.value = weather.temp
                weatherStateFlow.value = weather.weather
                dayTempStateFlow.value = weather.forecasts[0].dayTemp
                nightTempStateFlow.value = weather.forecasts[0].nightTemp
                forecastStateFlow.value = weather.forecasts
                refreshStateFlow.value = false
            }
    }

    private fun onError(errorMessage: String) {
        errorStateFlow.value = errorMessage
        refreshStateFlow.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}