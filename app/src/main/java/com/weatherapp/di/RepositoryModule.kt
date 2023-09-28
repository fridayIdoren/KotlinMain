package com.weatherapp.di

import com.weatherapp.data.WeatherAppRepositoryImpl
import com.weatherapp.domain.WeatherAppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun weatherRepository(repositoryImpl: WeatherAppRepositoryImpl): WeatherAppRepository
}
