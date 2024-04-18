package com.example.weatherapp.domain.usecases

import com.example.weatherapp.domain.models.Cities
import kotlinx.coroutines.flow.Flow

interface GetCitiesUseCase {
     suspend operator fun invoke(): Flow<Cities>
}