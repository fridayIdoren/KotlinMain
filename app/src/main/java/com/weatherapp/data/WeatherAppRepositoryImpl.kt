package com.weatherapp.data

import com.weatherapp.data.mappers.mapToDomain
import com.weatherapp.data.remote.WeatherApi
import com.weatherapp.domain.WeatherAppRepository
import com.weatherapp.domain.model.CityCoordinate
import com.weatherapp.domain.model.WeatherInfo
import javax.inject.Inject

const val apKey = "507f1e95b9effea0aab34e7c71cf8b68"

class WeatherAppRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi) :
    WeatherAppRepository {
    override suspend fun getCityCoordinate(city: String): CityCoordinate {
        return weatherApi.getCityCordinates(city, 1, apKey).map {
            with(it) {
                CityCoordinate(name, lat, lon, country, state)
            }
        }.first()
    }

    override suspend fun getWeatherInfo(lat: Double, long: Double): WeatherInfo {
        return weatherApi.getCityWeatherInfo(
            lat, long, apKey
        ).mapToDomain()
    }
}
