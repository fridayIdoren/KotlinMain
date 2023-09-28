package com.weatherapp.ui.weatherinfo

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.weatherapp.MainActivity
import com.weatherapp.R
import com.weatherapp.domain.model.WeatherInfo
import org.junit.Rule
import org.junit.Test

class WeatherInfoScreenKtTest {

    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    private val weatherInfo = WeatherInfo(
        tempMin = 18.75,
        temp = 20.2,
        feelsLike = 20.55,
        tempMax = 21.13,
        pressure = 1020,
        humidity = 87,
        weatherMainType = "Clouds",
        weatherMainDescription = "overcast clouds",
        weatherMainIcon = "04d",
        city = "Atlanta",
        windSpeed = 4.12
    )


    @Test
    fun testLoadingComposableIsRenderedWhenViewStateIsLoading() {

        val uiState = WeatherInfoViewModel.UIState.Loading

        composeRule.apply {
            activity.setContent { WeatherAppScreenView(uiState = uiState) {} }
            onNodeWithTag(composeRule.activity.getString(R.string.test_tag_circular_progress)).assertIsDisplayed()
        }
    }

    @Test
    fun testErrorTextIsShownWhenViewStateIsError() {

        val uiState = WeatherInfoViewModel.UIState.Error("Error loading weather info")

        composeRule.apply {
            activity.setContent { WeatherAppScreenView(uiState = uiState) {} }
            onNodeWithTag(composeRule.activity.getString(R.string.error_test_tag)).assertIsDisplayed()
        }
    }

    @Test
    fun testWeatherInfoIsShownWhenViewStateIsLoaded() {

        val uiState = WeatherInfoViewModel.UIState.Loaded(weatherInfo, "Today, 27 September")

        composeRule.apply {
            activity.setContent { WeatherAppScreenView(uiState = uiState) {} }
            onNodeWithTag(composeRule.activity.getString(R.string.test_tag_weather_icon)).assertIsDisplayed()
        }
    }

    @Test
    fun testClickLocationWidgetOpensCitiesBottomSheet() {

        val uiState = WeatherInfoViewModel.UIState.Loaded(weatherInfo, "Today, 27 September")

        composeRule.apply {
            activity.setContent { WeatherAppScreenView(uiState = uiState) {} }
            onAllNodesWithTag(composeRule.activity.getString(R.string.test_tag_location_widget)).onFirst()
                .performClick()
            onNodeWithTag(composeRule.activity.getString(R.string.test_tag_cities_bottom_sheet)).assertIsDisplayed()

        }
    }
}
