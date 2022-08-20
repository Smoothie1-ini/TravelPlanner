package com.smooth.travelplanner.presentation.auth.sign_in

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import com.smooth.travelplanner.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: BaseAuthRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private var _signInState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signInState = _signInState.asStateFlow()

    private val _signInData = MutableStateFlow(SignInData())
    val signInData = _signInData.asStateFlow()

    fun onEmailChange(email: String) {
        //TODO validation
        _signInData.value = _signInData.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        //TODO validation
        _signInData.value = _signInData.value.copy(password = password)
    }

    fun checkRememberMe() = viewModelScope.launch {
        try {
            _signInData.value = _signInData.value.copy(rememberMe = sharedPreferences.getBoolean("rm", false))
            if (_signInData.value.rememberMe) {
                val rmEmail = sharedPreferences.getString("rmEmail", "")
                val rmPassword = sharedPreferences.getString("rmPassword", "")
                onEmailChange(rmEmail ?: "")
                onPasswordChange(rmPassword ?: "")
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "checkRememberMe(): ${Response.Error(error[1])}"
            )
            _signInState.value = Response.Error(error[1])
        }
    }

    private fun saveRememberMe() = viewModelScope.launch {
        try {
            sharedPreferences.edit().putBoolean("rm", _signInData.value.rememberMe).apply()
            if (_signInData.value.rememberMe) {
                sharedPreferences.edit().putString("rmEmail", _signInData.value.email).apply()
                sharedPreferences.edit().putString("rmPassword", _signInData.value.password).apply()
            } else {
                sharedPreferences.edit().remove("rmEmail").apply()
                sharedPreferences.edit().remove("rmPassword").apply()
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "saveRememberMe(): ${Response.Error(error[1])}"
            )
            _signInState.value = Response.Error(error[1])
        }
    }

    fun onRememberMeChange() {
        _signInData.value = _signInData.value.copy(rememberMe = !_signInData.value.rememberMe)
    }

    fun validateData(email: String, password: String) {
        when {
            email.isEmpty() -> {
                _signInState.value = Response.Error("Email address can't be empty.")
            }
            password.isEmpty() -> {
                _signInState.value = Response.Error("Password can't be empty.")
            }
            else -> {
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) = viewModelScope.launch {
        try {
            _signInState.value = Response.Loading
            repository.signInWithEmailPassword(email, password)
            _signInState.value = Response.Success(true)
            saveRememberMe()
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "signIn(): ${Response.Error(error[1])}"
            )
            _signInState.value = Response.Error(error[1])
        }
    }

    fun resetState() {
        _signInState.value = Response.Success(false)
    }

    data class SignInData(
        val email: String = "",
        val password: String = "",
        val rememberMe: Boolean = false
    )
}