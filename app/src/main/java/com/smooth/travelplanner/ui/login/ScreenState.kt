package com.smooth.travelplanner.ui.login

sealed class ScreenState {
    object Success : ScreenState()
    data class Error(val message: String) : ScreenState()
    data class Message(val message: String) : ScreenState()
    object Loading : ScreenState()
    object Empty : ScreenState()
}
