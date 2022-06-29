package com.smooth.travelplanner.presentation.home.main_tabs.current_trips

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentTripsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val tripsRepository: BaseTripsRepository
) : ViewModel() {
    private val _tripsState = mutableStateOf<Response<List<Trip>>>(Response.Loading)
    val tripsState: State<Response<List<Trip>>>
        get() = _tripsState

    private val _tripDetailsData = MutableStateFlow(CurrentTripsData())
    val tripDetailsData: StateFlow<CurrentTripsData>
        get() = _tripDetailsData

    init {
        if (user != null)
            getTrips()
    }

    fun onDeleteDialogChange(trip: Trip?) {
        _tripDetailsData.value = _tripDetailsData.value.copy(tripToBeDeleted = trip)
        _tripDetailsData.value =
            _tripDetailsData.value.copy(deleteDialogState = !_tripDetailsData.value.deleteDialogState)
    }

    fun getTrips() = viewModelScope.launch {
        if (user != null)
            tripsRepository.getTrips(user.uid).collect {
                _tripsState.value = it
            }
    }

    fun deleteTrip(trip: Trip?) = viewModelScope.launch {
        if (trip != null) {
            tripsRepository.deleteTrip(trip.id).collect {
                //TODO store this state somewhere
            }
        }
    }

    data class CurrentTripsData(
        val deleteDialogState: Boolean = false,
        val tripToBeDeleted: Trip? = null
    )
}