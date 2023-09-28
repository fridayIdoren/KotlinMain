package com.weatherapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.weatherapp.R


val overPassFamily = FontFamily(
    Font(R.font.overpass_regular, FontWeight.Normal),
    Font(R.font.overpass_bold, FontWeight.Bold)
)

val Typography = Typography(defaultFontFamily = overPassFamily)
