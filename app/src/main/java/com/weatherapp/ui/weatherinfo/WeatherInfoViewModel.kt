package com.weatherapp.ui.weatherinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.PreferenceHelper
import com.weatherapp.domain.WeatherAppRepository
import com.weatherapp.domain.model.WeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler

@HiltViewModel
class
WeatherInfoViewModel @Inject constructor(
    private val weatherRepository: WeatherAppRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState as StateFlow<UIState>

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Some error occurred: $throwable")
    }

    init {
        fetchWeatherInfo()
    }

    private fun fetchWeatherInfo() {
        preferenceHelper.getCitySelection()?.let {
            fetchWeatherInfo(it)
        }
    }

    fun fetchWeatherInfo(city: String) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.emit(UIState.Loading)
            preferenceHelper.saveCitySelection(city)
            val coordinate = weatherRepository.getCityCoordinate(city)
            val weatherInfo = weatherRepository.getWeatherInfo(coordinate.lat, coordinate.long)
            _uiState.emit(UIState.Loaded(weatherInfo = weatherInfo, getTodaysDate()))
        }
    }


    fun fetchWeatherInfo(lat: Double, long: Double) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.emit(UIState.Loading)
            val weatherInfo = weatherRepository.getWeatherInfo(lat, long)
            val city = weatherInfo.city
            preferenceHelper.saveCitySelection(city)

            _uiState.emit(
                UIState.Loaded(weatherInfo = weatherInfo, getTodaysDate())
            )
        }
    }

    sealed class UIState {
        object Loading : UIState()
        data class Error(val message: String) : UIState()
        data class Loaded(
            val weatherInfo: WeatherInfo, val todaysDate: String
        ) : UIState()
    }

    companion object {

        fun getTodaysDate(): String {
            val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
            val currentDate = Date()
            return "Today, ${dateFormat.format(currentDate)}"
        }

        val usCities = listOf(
            "New York",
            "Los Angeles",
            "Chicago",
            "Houston",
            "Phoenix",
            "Philadelphia",
            "San Antonio",
            "San Diego",
            "Dallas",
            "San Jose",
            "Austin",
            "Jacksonville",
            "San Francisco",
            "Indianapolis",
            "Columbus",
            "Fort Worth",
            "Charlotte",
            "Seattle",
            "Denver",
            "El Paso",
            "Detroit",
            "Washington",
            "Boston",
            "Memphis",
            "Nashville",
            "Portland",
            "Oklahoma City",
            "Las Vegas",
            "Baltimore",
            "Louisville",
            "Milwaukee",
            "Albuquerque",
            "Tucson",
            "Fresno",
            "Sacramento",
            "Kansas City",
            "Long Beach",
            "Mesa",
            "Atlanta",
            "Colorado Springs",
            "Raleigh",
            "Miami",
            "Oakland",
            "Minneapolis",
            "Tulsa",
            "Cleveland",
            "Wichita",
            "Arlington"
        )

    }
}
