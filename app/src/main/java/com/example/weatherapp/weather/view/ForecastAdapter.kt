package com.example.weatherapp.weather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ForecastItemBinding
import com.example.weatherapp.domain.models.Forecast

class ForecastAdapter(
    private val context: Context,
    private val items: MutableList<Forecast> = mutableListOf(),
) : RecyclerView.Adapter<ForecastAdapter.ForecastItemViewHolder>() {

    fun setData(newItems: List<Forecast>) {
        items.clear()
        items.addAll(newItems)
    }

    fun updateData(newItems: List<Forecast>) {
        val diffUtils = ForecastDiffUtils(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        diffResult.dispatchUpdatesTo(this)
        setData(newItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastItemViewHolder {
        return ForecastItemViewHolder(
            ForecastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ForecastItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ForecastItemViewHolder(private val binding: ForecastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Forecast) {
            with(context) {
                binding.tvItemDate.text = model.date
                val dayTemp = getString(R.string.prefix_day) +
                        model.dayTemp.toString() + getString(R.string.postfix_degrees) +
                        getString(R.string.postfix_celsius_symbol)
                binding.tvItemDayTemp.text = dayTemp
                binding.tvItemDayWeather.text = model.dayWeather
                val nightTemp = getString(R.string.prefix_night) +
                        model.nightTemp.toString() + getString(R.string.postfix_degrees) +
                        getString(R.string.postfix_celsius_symbol)
                binding.tvItemNightTemp.text = nightTemp
                binding.tvItemNightWeather.text = model.nightWeather
            }
        }
    }
}