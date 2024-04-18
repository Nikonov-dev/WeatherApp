package com.example.weatherapp.cities.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapp.BaseExceptionHandler
import com.example.weatherapp.MyApplication
import com.example.weatherapp.R
import com.example.weatherapp.cities.viewmodel.CitiesViewModel
import com.example.weatherapp.databinding.FragmentCitiesBinding
import com.example.weatherapp.domain.models.City
import com.example.weatherapp.weather.view.collect
import javax.inject.Inject

class CitiesFragment : Fragment(R.layout.fragment_cities) {
    private val binding by viewBinding(FragmentCitiesBinding::bind)
    private val adapter by lazy { CitiesAdapter(viewModel::onCityClicked) }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(ViewModel::class.java) as CitiesViewModel
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as MyApplication)
            .componentHolder.citiesComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUp()
        viewModel.setUp()
    }

    private fun FragmentCitiesBinding.setUp() {
        with(binding) {
            rvCitiesList.setHasFixedSize(true)
            rvCitiesList.adapter = adapter
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { query ->
                        adapter.filter(query)
                    }
                    return true
                }
            })
        }
    }

    private fun CitiesViewModel.setUp() {
        with(viewModel) {
            progressStateFlow.collect(lifecycleScope, ::setProgress)
            errorStateFlow.collect(lifecycleScope, ::onError)
            citiesStateFlow.collect(lifecycleScope, ::setCities)
            onViewCreated()
        }
    }

    private fun setCities(cities: List<City>) {
        adapter.setData(cities)
    }

    private fun onError(errorMessage: String?) {
        errorMessage?.let { error ->
            when (error) {
                BaseExceptionHandler.NETWORK_ERROR -> Toast.makeText(
                    requireContext(),
                    getString(R.string.network_error_message),
                    Toast.LENGTH_LONG
                ).show()
                BaseExceptionHandler.ERROR -> Toast.makeText(
                    requireContext(),
                    getString(R.string.error_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setProgress(boolean: Boolean) {
        binding.progressBar.isVisible = boolean
    }
}