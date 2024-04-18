package com.example.weatherapp.di

import com.github.terrakok.cicerone.Router

interface NavigationDeps {
    fun router(): Router
}