package com.weatherapp.data.remote

import com.weatherapp.data.model.CityCoordinateResponse
import com.weatherapp.data.model.WeatherInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/geo/1.0/direct")
    suspend fun getCityCordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int,
        @Query("appid") apiKey: String
    ): List<CityCoordinateResponse>

    @GET("/data/2.5/weather?units=metric")
    suspend fun getCityWeatherInfo(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiKey: String
    ): WeatherInfoResponse
}
