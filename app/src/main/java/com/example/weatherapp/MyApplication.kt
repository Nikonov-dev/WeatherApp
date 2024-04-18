package com.example.weatherapp

import android.app.Application

class MyApplication : Application() {
    val componentHolder by lazy { ComponentHolder() }
}