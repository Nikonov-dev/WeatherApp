package com.example.weatherapp.domain.mappers

import com.example.weatherapp.data.models.CitiesDto
import com.example.weatherapp.data.models.CityDto
import com.example.weatherapp.data.network.CityResponse
import com.example.weatherapp.domain.models.Cities
import com.example.weatherapp.domain.models.City
import javax.inject.Inject

class CitiesMapper @Inject constructor() {
    operator fun invoke(citiesDto: CitiesDto): Cities {
        val cities = citiesDto.cities.map(::mapCityDtoToDomain)
        return Cities(
            cities = cities
        )
    }

    private fun mapCityDtoToDomain(cityDto: CityDto): City {
        return with(cityDto) {
            City(
                name = name,
                lat = lat,
                lon = lon
            )
        }
    }
}