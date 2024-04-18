package com.example.weatherapp.weather.view

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapp.domain.models.Forecast

class ForecastDiffUtils(
    private val oldList: List<Forecast>,
    private val newList: List<Forecast>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].date == newList[newItemPosition].date
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        val payload = ForecastPayload()
        if (oldItem.date != newItem.date) {
            payload.date = newItem.date
        }
        if (oldItem.dayTemp != newItem.dayTemp) {
            payload.dayTemp = newItem.dayTemp
        }
        if (oldItem.dayWeather != newItem.dayWeather) {
            payload.dayWeather = newItem.dayWeather
        }
        if (oldItem.nightTemp != newItem.nightTemp) {
            payload.nightTemp = newItem.nightTemp
        }
        if (oldItem.nightWeather != newItem.nightWeather) {
            payload.nightWeather = newItem.nightWeather
        }
        return payload
    }
}