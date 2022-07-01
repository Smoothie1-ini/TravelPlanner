package com.smooth.travelplanner.presentation.home.trip_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import com.smooth.travelplanner.domain.repository.BaseMainRepository
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
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
    private val mainRepository: BaseMainRepository,
    cachedMainRepository: BaseCachedMainRepository,
    private val tripsRepository: BaseTripsRepository,
    private val tripDaysRepository: BaseTripDaysRepository
) : ViewModel() {
    val currentTripsWithSubCollectionsState = cachedMainRepository.tripsWithSubCollectionsState

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
            addTrip()
        } else {
            updateTrip(tripId)
        }
        mainRepository.refreshData(user)
    }

    fun onDeleteDialogChange(tripDay: TripDay?) {
        _tripDetailsData.value = _tripDetailsData.value.copy(tripDayToBeDeleted = tripDay)
        _tripDetailsData.value =
            _tripDetailsData.value.copy(deleteDialogState = !_tripDetailsData.value.deleteDialogState)
    }

    fun getCurrentTripOrNull(tripId: String): Trip? {
        for (trip in (currentTripsWithSubCollectionsState.value as Response.Success).data) {
            if (trip.id == tripId) {
                onTitleChange(trip.title)
                onDescriptionChange(trip.description)
                return trip
            }
        }
        return null
    }

    private fun addTrip() = viewModelScope.launch {
        val trip = Trip(
            userId = user!!.uid,
            title = _tripDetailsData.value.title,
            description = _tripDetailsData.value.description
        )
        tripsRepository.addTrip(trip.toMap()).collect {
            _tripState.value = it
        }
    }

    private fun updateTrip(tripId: String) = viewModelScope.launch {
        val trip = Trip(
            userId = user!!.uid,
            title = _tripDetailsData.value.title,
            description = _tripDetailsData.value.description
        )
        tripsRepository.updateTrip(tripId, trip.toMap()).collect {
            _tripState.value = it
        }
    }

    fun deleteTripDay(trip: Trip?, tripDay: TripDay?) = viewModelScope.launch {
        if (trip != null && tripDay != null) {
            tripDaysRepository.deleteTripDay(trip.id, tripDay.id).collect {
                _tripState.value = it
            }
            mainRepository.refreshData(user)
        }
    }

    data class TripDetailsData(
        val title: String = "",
        val description: String = "",
        val deleteDialogState: Boolean = false,
        val tripDayToBeDeleted: TripDay? = null
    )
}