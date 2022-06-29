package com.smooth.travelplanner.presentation.home.trip_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import com.smooth.travelplanner.presentation.common.multi_fab.MultiFabItem
import com.smooth.travelplanner.util.toMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val tripsRepository: BaseTripsRepository
) : ViewModel() {
    private val _tripDetailsData = MutableStateFlow(TripDetailsData())
    val tripDetailsData: StateFlow<TripDetailsData>
        get() = _tripDetailsData

    private val _tripState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val tripState: State<Response<Boolean>> = _tripState

    val fabItems = listOf(
        MultiFabItem(
            0,
            R.drawable.ic_confirm,
            "Save trip"
        ),
        MultiFabItem(
            1,
            R.drawable.ic_add,
            "Add day"
        )
    )

    fun onTitleChange(title: String) {
        _tripDetailsData.value = _tripDetailsData.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _tripDetailsData.value = _tripDetailsData.value.copy(description = description)
    }

    fun onFabSaveTripClicked(tripId: String) {
        if (tripId.isEmpty()) {
            Log.d("TripDetailsViewModel", "Add trip")
            addTrip()
        } else {
            Log.d("TripDetailsViewModel", "Update trip")
            updateTrip(tripId)
        }
    }

    private fun addTrip() = viewModelScope.launch {
        val trip = Trip(
            idUser = user!!.uid,
            title = _tripDetailsData.value.title,
            description = _tripDetailsData.value.description
        )
        tripsRepository.addTrip(trip.toMap()).collect {
            _tripState.value = it
        }
    }

    private fun updateTrip(tripId: String) = viewModelScope.launch {
        val trip = Trip(
            idUser = user!!.uid,
            title = _tripDetailsData.value.title,
            description = _tripDetailsData.value.description
        )
        tripsRepository.updateTrip(tripId, trip.toMap()).collect {
            _tripState.value = it
        }
    }

    fun deleteTripDay() {
        //TODO trip deletion
    }

    data class TripDetailsData(
        val title: String = "",
        val description: String = ""
    )
}