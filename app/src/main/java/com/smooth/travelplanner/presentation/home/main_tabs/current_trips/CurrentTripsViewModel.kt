package com.smooth.travelplanner.presentation.home.main_tabs.current_trips

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentTripsViewModel @Inject constructor(
    user: FirebaseUser?,
    private val tripsRepository: BaseTripsRepository
) : ViewModel() {
    private val _tripsState = mutableStateOf<Response<List<Trip>>>(Response.Loading)
    val tripsState: State<Response<List<Trip>>>
        get() = _tripsState

    init {
        if (user != null)
            getTrips(Firebase.auth.currentUser!!.uid)
    }

    private fun getTrips(idUser: String) = viewModelScope.launch {
        tripsRepository.getTrips(idUser).collect {
            _tripsState.value = it
        }
    }

    fun deleteTrip() {
        //TODO trip deletion
    }
}