package com.weatherapp.domain.model


data class CityCoordinate(
    val name: String,
    val lat: Double,
    val long: Double,
    val country: String,
    val state: String
)
