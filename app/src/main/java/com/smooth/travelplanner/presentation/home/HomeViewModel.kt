package com.smooth.travelplanner.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BaseAuthRepository
) : ViewModel() {
    private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?>
        get() = _firebaseUser

    private var _homeState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val homeState = _homeState.asStateFlow()

    private val _homeData = MutableStateFlow(HomeData())
    val homeData = _homeData.asStateFlow()

    fun onLogOutDialogChange() {
        _homeData.value = _homeData.value.copy(logOutDialogState = !_homeData.value.logOutDialogState)
    }

    fun onTopBarChange(topBarState: Boolean) {
        _homeData.value = _homeData.value.copy(topBarState = topBarState)
    }

    fun onBottomBarChange(bottomBarState: Boolean) {
        _homeData.value = _homeData.value.copy(bottomBarState = bottomBarState)
    }

    fun onSearchBarValueChange(searchBar: String) {
        _homeData.value = _homeData.value.copy(searchBarValue = searchBar)
    }

    fun signOut() = viewModelScope.launch {
        try {
            val user = repository.signOut()
            if (user == null)
                _homeState.value = Response.Message("Logout successful.")
            else
                _homeState.value = Response.Error("Logout failure.")
            getCurrentUser()
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(
                "HomeViewModel",
                "signOut(): ${Response.Error(error[1])}"
            )
            _homeState.value = Response.Error(error[1])
        }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val user = repository.getUser()
        _firebaseUser.value = user
    }

    data class HomeData(
        val searchBarValue: String = "",
        val logOutDialogState: Boolean = false,
        val topBarState: Boolean = false,
        val bottomBarState: Boolean = false
    )
}