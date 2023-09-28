package com.weatherapp.domain.model

data class UserDetailDomain(
    val username: String,
    val avatarUrl: String,
    val githubUrl: String,
    val name: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val repos: Int,
    val bio: String
)


data class WeatherInfo(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long,
    val weatherMainType: String,
    val weatherMainDescription: String,
    val weatherMainIcon: String,
    val windSpeed:Double,
    val city:String
) {


    fun getIconUrl(): String {
        return "https://openweathermap.org/img/wn/$weatherMainIcon@4x.png"
    }
}
