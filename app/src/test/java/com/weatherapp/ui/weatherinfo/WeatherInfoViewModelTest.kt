package com.weatherapp.ui.weatherinfo

import app.cash.turbine.test
import com.weatherapp.data.PreferenceHelper
import com.weatherapp.domain.WeatherAppRepository
import com.weatherapp.domain.model.CityCoordinate
import com.weatherapp.domain.model.WeatherInfo
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class WeatherInfoViewModelTest {

    private val weatherRepository = mockk<WeatherAppRepository>()
    private val preferenceHelper = mockk<PreferenceHelper>()

    private val sut = WeatherInfoViewModel(weatherRepository, preferenceHelper)

    private val city = "Atlanta"
    private val weatherInfo = WeatherInfo(
        tempMin = 18.75,
        temp = 20.2,
        feelsLike = 20.55,
        tempMax = 21.13,
        pressure = 1020,
        humidity = 87,
        weatherMainType = "Clouds",
        weatherMainDescription = "overcsat clouds",
        weatherMainIcon = "04d",
        city = city,
        windSpeed = 4.12
    )

    private val latitude = 33.749
    private val longitude = -84.388

    private val coordinate =
        CityCoordinate(name = city, lat = latitude, long = longitude, country = "USA", "")


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Weather Info Fetched successfully - correct state emitted `() = runTest {

        coEvery { weatherRepository.getCityCoordinate(city) } returns coordinate
        coEvery { weatherRepository.getWeatherInfo(latitude, longitude) } returns weatherInfo
        every { preferenceHelper.getCitySelection() } returns city
        every { preferenceHelper.saveCitySelection(city) } returns Unit

        sut.uiState.test {
            sut.fetchWeatherInfo(city)

            assertEquals(WeatherInfoViewModel.UIState.Loading, awaitItem())


            val expected = weatherInfo
            val content = awaitItem()


            assertEquals(WeatherInfoViewModel.UIState.Loaded(expected, getTodaysDate()), content)
            assertEquals(
                20.2,
                (content as WeatherInfoViewModel.UIState.Loaded).weatherInfo.temp,
                0.0
            )

            cancelAndConsumeRemainingEvents()
        }

    }

    @Before
    fun setUp() {
        // setting up test dispatcher as main dispatcher for coroutines
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        // removing the test dispatcher
        Dispatchers.resetMain()
    }

    companion object {
        fun getTodaysDate(): String {
            val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
            val currentDate = Date()
            return "Today, ${dateFormat.format(currentDate)}"
        }
    }
}
