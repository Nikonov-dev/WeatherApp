package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.weatherapp.navigation.Screen
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.location.LocationServices

class MainActivity : FragmentActivity(R.layout.activity_main) {
    companion object {
        private const val MIN_BACK_STACK_SIZE_TO_EXIT = 1
    }

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val navigator by lazy {
        AppNavigator(
            this,
            R.id.flMainContainer,
            supportFragmentManager,
        )
    }
    private val router by lazy { Router() }
    private val cicerone by lazy { Cicerone.create(router) }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requestLocation()
        } else {
            showAlertDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_WeatherApp)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        cicerone.getNavigatorHolder().setNavigator(navigator)
        with(application as MyApplication) {
            componentHolder.initNavigationComponent(cicerone)
        }
        setOnBackPressedActions()
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onStop() {
        super.onStop()
        cicerone.getNavigatorHolder().removeNavigator()
    }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val lat = location.latitude
                        val lon = location.longitude
                        val screen = Screen.weatherScreen(lat = lat, lon = lon)
                        router.navigateTo(screen)
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage(R.string.dialog_message_to_get_geo_permission)
            setPositiveButton(R.string.dialog_positive_btn_okay) { _, _ ->
                requestLocation()
            }
            create()
        }.show()
    }

    private fun setOnBackPressedActions() {
        onBackPressedDispatcher.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (supportFragmentManager.backStackEntryCount == MIN_BACK_STACK_SIZE_TO_EXIT) {
                        moveTaskToBack(true)
                    } else {
                        router.exit()
                    }
                }
            })
    }
}