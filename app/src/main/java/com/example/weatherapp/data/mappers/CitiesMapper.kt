package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.models.CitiesDto
import com.example.weatherapp.data.models.CityDto
import com.example.weatherapp.data.network.CitiesResponse
import com.example.weatherapp.data.network.CityResponse
import javax.inject.Inject

class CitiesMapper @Inject constructor() {
    operator fun invoke(citiesResponse: CitiesResponse): CitiesDto {
        val cities = citiesResponse.cities.map(::mapCityResponseToDto)
        return CitiesDto(
            cities = cities
        )
    }

    private fun mapCityResponseToDto(cityResponse: CityResponse): CityDto {
        return with(cityResponse) {
            CityDto(
                name = name,
                lat = lat,
                lon = lon
            )
        }
    }
}