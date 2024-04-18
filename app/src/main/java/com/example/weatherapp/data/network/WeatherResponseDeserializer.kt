package com.example.weatherapp.data.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class WeatherResponseDeserializer : JsonDeserializer<WeatherResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WeatherResponse {
        val jsonObject = json?.asJsonObject
        val factObject = jsonObject?.getAsJsonObject("fact")
        val temp = factObject?.get("temp")?.asInt ?: 0
        val weather = factObject?.get("condition")?.asString ?: ""
        val forecasts = mutableListOf<ForecastResponse>()
        val jsonForecasts = jsonObject?.getAsJsonArray("forecasts")
        if (jsonForecasts != null) {
            for (i in 0 until jsonForecasts.size()) {
                val jsonForecast = jsonForecasts.get(i).asJsonObject
                val date = jsonForecast.get("date").asString
                val parts = jsonForecast.getAsJsonObject("parts")
                val day = parts.getAsJsonObject("day")
                val night = parts.getAsJsonObject("night")
                val dayTemp = day.get("temp_avg").asInt
                val dayWeather = day.get("condition").asString
                val nightTemp = night.get("temp_avg").asInt
                val nightWeather = night.get("condition").asString
                forecasts.add(
                    ForecastResponse(
                        date = date,
                        dayTemp = dayTemp,
                        dayWeather = dayWeather,
                        nightTemp = nightTemp,
                        nightWeather = nightWeather
                    )
                )
            }
        }

        return WeatherResponse(weather = weather, temp = temp, forecasts = forecasts)
    }
}