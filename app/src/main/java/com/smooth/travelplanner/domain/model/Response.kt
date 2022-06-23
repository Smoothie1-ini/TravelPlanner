package com.smooth.travelplanner.domain.model

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
    data class Message(val message: String) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
