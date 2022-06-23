package com.smooth.travelplanner.presentation.login.password_reset

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordResetViewModel @Inject constructor(
    private val repository: BaseAuthRepository
) : ViewModel() {
    private var _passwordResetState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val passwordResetState: StateFlow<Response<Boolean>>
        get() = _passwordResetState

    private val _passwordResetData = MutableStateFlow(PasswordResetData())
    val passwordResetData: StateFlow<PasswordResetData>
        get() = _passwordResetData

    fun onEmailChanged(email: String) {
        //TODO validation
        _passwordResetData.value = _passwordResetData.value.copy(email = email)
    }

    fun validateData(email: String) {
        when {
            email.isEmpty() -> {
                _passwordResetState.value = Response.Error("Email address can't be empty.")
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
                _passwordResetState.value = Response.Message("Password reset sent on email.")
            else
                _passwordResetState.value = Response.Message("Password reset sending failure.")
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "SignInViewModel",
                "signIn(): ${Response.Error(error[1])}"
            )
            _passwordResetState.value = Response.Error(error[1])
        }
    }

    data class PasswordResetData(
        val email: String = ""
    )
}