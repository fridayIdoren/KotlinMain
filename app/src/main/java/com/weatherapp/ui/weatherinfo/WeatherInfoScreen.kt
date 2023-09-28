package com.weatherapp.ui.weatherinfo

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.weatherapp.R
import com.weatherapp.ui.composables.PrimaryText
import com.weatherapp.ui.cities.CitiesBottomSheet
import com.weatherapp.ui.theme.overPassFamily


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherInfoScreen() {

    val viewModel = hiltViewModel<WeatherInfoViewModel>()
    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        ),
        onPermissionsResult = { result ->
            val granted = result.values.all { true }
            if (granted) {
                getCurrentLocation(context) { lat, long ->
                    viewModel.fetchWeatherInfo(lat, long)
                }
            } else {
                viewModel.fetchWeatherInfo("New York")
            }
        }
    )

    if (!locationPermissionsState.allPermissionsGranted) {
        LaunchedEffect(key1 = 2) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }

    WeatherAppScreenView(uiState.value) {
        viewModel.fetchWeatherInfo(it)
    }
}

@Composable
fun WeatherAppScreenView(uiState: WeatherInfoViewModel.UIState, callback: (String) -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(71, 191, 223, 255), Color(74, 145, 255, 255)),

                    )
            )
    ) {

        when (uiState) {
            is WeatherInfoViewModel.UIState.Loading -> {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(
                            stringResource(R.string.test_tag_circular_progress)
                        )
                )
            }

            is WeatherInfoViewModel.UIState.Loaded -> {

                val weatherInfo = uiState.weatherInfo
                val city = weatherInfo.city
                val date = uiState.todaysDate

                val showSheet = remember { mutableStateOf(false) }

                val citySelection = remember { mutableStateOf(city) }


                if (showSheet.value) {
                    CitiesBottomSheet(onItemClicked = { selection ->
                        showSheet.value = false
                        callback(selection)
                    }, onDismiss = {
                        showSheet.value = false
                    })
                }


                Column(modifier = Modifier.fillMaxSize()) {

                    Row(
                        modifier = Modifier
                            .padding(
                                start = 32.dp,
                                top = 52.dp,
                                end = 32.dp
                            )
                            .clickable { showSheet.value = true }
                            .testTag(stringResource(id = R.string.test_tag_location_widget)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = "Location Icon",
                            contentScale = ContentScale.None
                        )

                        Spacer(Modifier.width(20.dp))

                        Text(
                            text = citySelection.value,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = overPassFamily,
                                fontWeight = FontWeight(700),
                                color = Color(0xFFFFFFFF),
                            )
                        )

                        Spacer(Modifier.width(20.dp))


                        Image(
                            painter = painterResource(id = R.drawable.ic_drop_down),
                            contentDescription = "Drop down Icon",
                            contentScale = ContentScale.None,
                        )
                    }


                    Spacer(Modifier.height(66.dp))


                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {

                        AsyncImage(
                            model = weatherInfo.getIconUrl(),
                            contentDescription = null,
                            modifier = Modifier.testTag(stringResource(id = R.string.test_tag_weather_icon))
                        )

                    }

                    Spacer(Modifier.height(34.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                            .border(
                                width = 2.dp,
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 20.dp)
                            )
                            .height(435.dp)
                            .background(
                                color = Color(0x4DFFFFFF),
                                shape = RoundedCornerShape(size = 20.dp)
                            )

                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                        ) {


                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = date,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = overPassFamily,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFFFFFFF),
                                )
                            )

                            Row {
                                Text(
                                    text = weatherInfo.temp.toString(),
                                    style = TextStyle(
                                        fontSize = 100.sp,
                                        fontFamily = overPassFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFFFFFFF),
                                    )
                                )

                                Text(
                                    text = "°",
                                    style = TextStyle(
                                        fontSize = 72.sp,
                                        fontFamily = overPassFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFFFFFFF),
                                    )
                                )
                            }

                            Spacer(Modifier.height(20.dp))

                            Text(
                                text = weatherInfo.weatherMainDescription,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontFamily = overPassFamily,
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFFFFFFFF),
                                )
                            )

                            Spacer(Modifier.height(20.dp))



                            Column {

                                Row(
                                    modifier = Modifier.fillMaxWidth(0.7f),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_pressure),
                                        contentDescription = "Windy Icon",
//                                        contentScale = ContentScale.None
                                    )

                                    Text(
                                        text = "Pressure",
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = overPassFamily,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )

                                    Text(
                                        text = "${weatherInfo.pressure} hPa",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = overPassFamily,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )
                                }

                                Spacer(Modifier.height(20.dp))


                                Row(
                                    modifier = Modifier.fillMaxWidth(0.7f),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_humidity),
                                        contentDescription = "Humidity Icon",
//                                        contentScale = ContentScale.None
                                    )

                                    Text(
                                        text = "Humidity",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = overPassFamily,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )

                                    Text(
                                        text = "${weatherInfo.humidity}%",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = overPassFamily,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )


                                }
                                Spacer(Modifier.height(20.dp))


                                Row(
                                    modifier = Modifier.fillMaxWidth(0.7f),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_windy),
                                        contentDescription = "Windy Icon",
//                                        contentScale = ContentScale.None
                                    )

                                    Text(
                                        text = "Wind",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = overPassFamily,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )
                                    Text(
                                        text = "${weatherInfo.windSpeed} km/h",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontFamily = overPassFamily,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )
                                }


                            }

                        }


                    }


                }
//
            }

            is WeatherInfoViewModel.UIState.Error -> {
                val errorMsg = uiState.message
                Text(
                    text = "Oops $errorMsg", modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(
                            stringResource(R.string.error_test_tag),
                        ),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = overPassFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}


@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val defaultLat = 45.4206749
    val defaultLong = -122.670649
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            } else {
                callback(defaultLat, defaultLong)
            }
        }
        .addOnFailureListener { exception ->
            callback(defaultLat, defaultLong)
            exception.printStackTrace()
        }
}

