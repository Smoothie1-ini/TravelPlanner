package com.smooth.travelplanner.ui.login.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.data.remote.BaseAuthRepository
import com.smooth.travelplanner.ui.login.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val repository: BaseAuthRepository
): ViewModel() {
    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?>
        get() = _firebaseUser

    private var _signUpState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val signUpState: StateFlow<ScreenState>
        get() = _signUpState

    fun validateData(email: String, password: String, repeatPassword: String) {
        when {
            email.isEmpty() -> {
                _signUpState.value = ScreenState.Error("Email address can't be empty.")
            }
            password.isEmpty() -> {
                _signUpState.value = ScreenState.Error("Password can't be empty.")
            }
            password != repeatPassword -> {
                _signUpState.value = ScreenState.Error("Passwords must be equal.")
            }
            else -> {
                signUp(email, password)
            }
        }
    }

    private fun signUp(email: String, password: String) = viewModelScope.launch {
        try {
            val user = repository.signUpWithEmailPassword(email, password)
            user?.let {
                _firebaseUser.value = it
                _signUpState.value = ScreenState.Success
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "signIn(): ${ScreenState.Error(error[1])}"
            )
            _signUpState.value = ScreenState.Error(error[1])
        }
    }
}