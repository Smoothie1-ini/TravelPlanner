package com.smooth.travelplanner.ui.login.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.data.remote.BaseAuthRepository
import com.smooth.travelplanner.ui.login.ScreenState
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

    private var _signInState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val signInState: StateFlow<ScreenState>
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
                _signInState.value = ScreenState.Error("Email address can't be empty.")
            }
            password.isEmpty() -> {
                _signInState.value = ScreenState.Error("Password can't be empty.")
            }
            else -> {
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) = viewModelScope.launch {
        try {
            _signInState.value = ScreenState.Loading
            val user = repository.signInWithEmailPassword(email, password)
            user?.let {
                _firebaseUser.value = it
                _signInState.value = ScreenState.Success
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "signIn(): ${ScreenState.Error(error[1])}"
            )
            _signInState.value = ScreenState.Error(error[1])
        }
    }

    fun signOut() = viewModelScope.launch {
        try {
            val user = repository.signOut()
            if (user == null)
                _signInState.value = ScreenState.Message("Logout successful.")
            else
                _signInState.value = ScreenState.Error("Logout failure.")
            getCurrentUser()
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "signIn(): ${ScreenState.Error(error[1])}"
            )
            _signInState.value = ScreenState.Error(error[1])
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        val user = repository.getUser()
        _firebaseUser.value = user
    }
}