package com.smooth.travelplanner.ui.login.password_reset

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

class PasswordResetViewModel @Inject constructor(
    private val repository: BaseAuthRepository
) : ViewModel() {
    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?>
        get() = _firebaseUser

    private var _passwordResetState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val passwordResetState: StateFlow<ScreenState>
        get() = _passwordResetState

    fun validatePasswordReset(email: String) {
        when {
            email.isEmpty() -> {
                _passwordResetState.value = ScreenState.Error("Email address can't be empty.")
            }
            else -> {
                sendPasswordReset(email)
            }
        }
    }

    private fun sendPasswordReset(email: String) = viewModelScope.launch {
        try {
            val result = repository.sendPasswordReset(email)
            if (result)
                _passwordResetState.value = ScreenState.Message("Password reset sent on email.")
            else
                _passwordResetState.value = ScreenState.Message("Password reset sending failure.")
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "signIn(): ${ScreenState.Error(error[1])}"
            )
            _passwordResetState.value = ScreenState.Error(error[1])
        }
    }
}