package com.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.weatherapp.ui.theme.WeatherAppTheme
import com.weatherapp.ui.weatherinfo.WeatherInfoScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold { padding ->
                    print(padding)
                    Surface(
                        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                    ) {
                        WeatherInfoScreen()
                    }
                }
            }
        }
    }
}
