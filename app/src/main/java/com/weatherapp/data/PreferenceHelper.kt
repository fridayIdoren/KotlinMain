package com.weatherapp.data

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val CITY = "city_selection"
    }

    fun saveCitySelection(city: String) {
        sharedPreferences.edit().putString(CITY, city).apply()
    }


    fun getCitySelection(): String? {
        return sharedPreferences.getString(CITY, null)
    }
}
