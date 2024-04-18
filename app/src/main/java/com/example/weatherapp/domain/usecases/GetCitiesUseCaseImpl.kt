package com.example.weatherapp.domain.usecases

import com.example.weatherapp.domain.WeatherAndCitiesRepository
import com.example.weatherapp.domain.mappers.CitiesMapper
import com.example.weatherapp.domain.models.Cities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCitiesUseCaseImpl @Inject constructor(
    private val repository: WeatherAndCitiesRepository,
    private val mapper: CitiesMapper,
) : GetCitiesUseCase {
    override suspend operator fun invoke(): Flow<Cities> {
        return repository.getCities().map(mapper::invoke)
    }
}