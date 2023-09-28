package com.weatherapp.domain

import com.weatherapp.domain.model.CityCoordinate
import com.weatherapp.domain.model.WeatherInfo

interface WeatherAppRepository {

    suspend fun getCityCoordinate(city: String): CityCoordinate

    suspend fun getWeatherInfo(lat: Double, long: Double): WeatherInfo
}
