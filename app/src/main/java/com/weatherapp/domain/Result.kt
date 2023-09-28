package com.weatherapp.domain

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val errorMsg: String) : Result<Nothing>()


}
