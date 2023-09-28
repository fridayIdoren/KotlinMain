package com.weatherapp.data.mappers

import com.weatherapp.data.model.WeatherInfoResponse
import com.weatherapp.domain.model.WeatherInfo
import java.util.Locale

fun WeatherInfoResponse.mapToDomain() = WeatherInfo(
    temp = main.temp,
    feelsLike = main.feelsLike,
    tempMax = main.tempMax,
    tempMin = main.tempMin,
    pressure = main.pressure,
    humidity = main.humidity,
    windSpeed = wind.speed,
    weatherMainType = weather.first().main,
    weatherMainDescription = weather.first().description.capitalizeWords(),
    weatherMainIcon = weather.first().icon,
    city = name
)


fun String.capitalizeWords(): String {
    val words = this.split(" ")
    val capitalizedWords =
        words.map { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }
    return capitalizedWords.joinToString(" ")
}
