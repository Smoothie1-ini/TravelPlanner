package com.smooth.travelplanner.ui.login.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: BaseAuthRepository
) : ViewModel() {
    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?>
        get() = _firebaseUser

    private var _signInState = MutableStateFlow<Response>(Response.Empty)
    val signInState: StateFlow<Response>
        get() = _signInState

    private val _signInData = MutableStateFlow(SignInData())
    val signInData: StateFlow<SignInData>
        get() = _signInData

    fun onEmailChanged(email: String) {
        //TODO validation
        _signInData.value = _signInData.value.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        //TODO validation
        _signInData.value = _signInData.value.copy(password = password)
    }

    //TODO preserve it in shared preferences
    fun onRememberMeChanged() {
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
                _firebaseUser.value = it
                _signInState.value = Response.Success
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

    data class SignInData(
        val email: String = "",
        val password: String = "",
        val rememberMe: Boolean = false
    )
}