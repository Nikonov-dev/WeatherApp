package com.example.weatherapp.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CitiesResponseDeserializer : JsonDeserializer<CitiesResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CitiesResponse {
        val jsonObject = json?.asJsonObject
        val cities = mutableListOf<CityResponse>()
        val geoNames = jsonObject?.getAsJsonArray("geonames")
        if (geoNames != null) {
            for (i in 0 until geoNames.size()) {
                val geoName = geoNames.get(i).asJsonObject
                val name = geoName.get("name").asString
                val lon = geoName.get("lng").asDouble
                val lat = geoName.get("lat").asDouble
                cities.add(CityResponse(name = name, lat = lat, lon = lon))
            }
        }

        return CitiesResponse(cities = cities)
    }
}