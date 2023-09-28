package com.weatherapp.di

import android.content.Context
import android.content.SharedPreferences
import com.weatherapp.data.WeatherAppRepositoryImpl
import com.weatherapp.domain.WeatherAppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("weather_app_pref", Context.MODE_PRIVATE)
    }
}
