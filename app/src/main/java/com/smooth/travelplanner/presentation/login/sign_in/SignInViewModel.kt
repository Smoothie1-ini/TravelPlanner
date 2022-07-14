package com.smooth.travelplanner.presentation.login.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: BaseAuthRepository
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

    //TODO preserve it in shared preferences
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
            val user = repository.signInWithEmailPassword(email, password)
            user?.let {
                _signInState.value = Response.Success(true)
            }
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
        val email: String = "test@test.pl",
        val password: String = "test123",
        val rememberMe: Boolean = false
    )
}