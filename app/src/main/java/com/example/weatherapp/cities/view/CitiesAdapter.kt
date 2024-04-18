package com.example.weatherapp.cities.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.CitiesItemBinding
import com.example.weatherapp.domain.models.City

class CitiesAdapter(
    private val onCityClick: (Double, Double) -> Unit,
    private val items: MutableList<City> = mutableListOf(),
) : RecyclerView.Adapter<CitiesAdapter.CitiesItemViewHolder>() {
    private val originalItems: MutableList<City> = mutableListOf()

    fun setData(newItems: List<City>) {
        items.clear()
        items.addAll(newItems)
        originalItems.clear()
        originalItems.addAll(newItems)
    }

    fun filter(query: String) {
        items.clear()
        if (query.isEmpty()) {
            items.addAll(originalItems)
        } else {
            items.addAll(originalItems.filter { city ->
                city.name.contains(
                    query,
                    ignoreCase = true
                )
            })
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesItemViewHolder {
        return CitiesItemViewHolder(
            CitiesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CitiesItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CitiesItemViewHolder(private val binding: CitiesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: City) {
            binding.tvCityName.text = model.name
            itemView.setOnClickListener { onCityClick(model.lat, model.lon) }
        }
    }
}