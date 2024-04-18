package com.example.weatherapp.weather.view

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapp.BaseExceptionHandler.Companion.ERROR
import com.example.weatherapp.BaseExceptionHandler.Companion.NETWORK_ERROR
import com.example.weatherapp.MyApplication
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.domain.models.Forecast
import com.example.weatherapp.weather.viewmodel.WeatherViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    companion object {
        private const val LATITUDE_KEY = "lat"
        private const val LONGITUDE_KEY = "lon"
        fun getNewInstance(
            lat: Double,
            lon: Double,
        ): WeatherFragment {
            val fragment = WeatherFragment()
            val bundle: Bundle = Bundle().apply {
                putDouble(LATITUDE_KEY, lat)
                putDouble(LONGITUDE_KEY, lon)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding by viewBinding(FragmentWeatherBinding::bind)
    private val adapter by lazy { ForecastAdapter(requireContext()) }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(ViewModel::class.java) as WeatherViewModel
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication)
            .componentHolder.weatherComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lat = arguments?.getDouble(LATITUDE_KEY)
        val lon = arguments?.getDouble(LONGITUDE_KEY)
        binding.setUp(lat, lon)
        viewModel.setUp(lat, lon)
    }

    private fun FragmentWeatherBinding.setUp(lat: Double?, lon: Double?) {
        with(binding) {
            rvNextDaysWeather.setHasFixedSize(true)
            rvNextDaysWeather.adapter = adapter
            btnCities.setOnClickListener { viewModel.onCitiesClicked() }
            lat?.let {
                lon?.let {
                    btnCities.text = getCityName(lat, lon)
                    swipeRefresh.setOnRefreshListener { viewModel.onRefresh(lat, lon) }
                }
            }
        }
    }

    private fun WeatherViewModel.setUp(lat: Double?, lon: Double?) {
        with(viewModel) {
            refreshStateFlow.collect(lifecycleScope, ::setRefresh)
            errorStateFlow.collect(lifecycleScope, ::onError)
            tempStateFlow.collect(lifecycleScope, ::setTemp)
            weatherStateFlow.collect(lifecycleScope, ::setWeather)
            dayTempStateFlow.collect(lifecycleScope, ::setDayTemp)
            nightTempStateFlow.collect(lifecycleScope, ::setNightTemp)
            forecastStateFlow.collect(lifecycleScope, ::updateForecast)
            lat?.let {
                lon?.let {
                    onViewCreated(lat, lon)
                }
            }
        }
    }

    private fun getCityName(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                return address.locality
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun setTemp(temp: Int) {
        val tempUi = temp.toString() + getString(R.string.postfix_degrees) +
                getString(R.string.postfix_celsius_symbol)
        binding.tvTemp.text = tempUi
    }

    private fun setDayTemp(temp: Int) {
        val tempUi = getString(R.string.prefix_day) + temp.toString() +
                getString(R.string.postfix_degrees) +
                getString(R.string.postfix_celsius_symbol)
        binding.tvDayTemp.text = tempUi
    }

    private fun setNightTemp(temp: Int) {
        val tempUi = getString(R.string.prefix_night) + temp.toString() +
                getString(R.string.postfix_degrees) +
                getString(R.string.postfix_celsius_symbol)
        binding.tvNightTemp.text = tempUi
    }

    private fun setWeather(weather: String) {
        binding.tvWeather.text = weather
    }

    private fun updateForecast(newItems: List<Forecast>) {
        adapter.updateData(newItems)
    }

    private fun onError(errorMessage: String?) {
        errorMessage?.let { error ->
            when (error) {
                NETWORK_ERROR -> Toast.makeText(
                    requireContext(),
                    getString(R.string.network_error_message),
                    Toast.LENGTH_LONG
                ).show()
                ERROR -> Toast.makeText(
                    requireContext(),
                    getString(R.string.error_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setRefresh(boolean: Boolean) {
        binding.swipeRefresh.isRefreshing = boolean
    }
}

fun <T> StateFlow<T>.collect(
    lifecycleScope: LifecycleCoroutineScope,
    collector: (T) -> Unit
) {
    lifecycleScope.launch { collect(collector) }
}