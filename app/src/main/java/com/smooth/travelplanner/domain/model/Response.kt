package com.smooth.travelplanner.domain.model

sealed class Response {
    object Success : Response()
    data class Error(val message: String) : Response()
    data class Message(val message: String) : Response()
    object Loading : Response()
    object Empty : Response()
}
