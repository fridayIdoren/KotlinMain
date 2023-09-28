package com.weatherapp.data.model


data class CityCoordinateResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)
