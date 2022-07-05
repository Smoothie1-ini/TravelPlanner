package com.smooth.travelplanner.presentation.home.trip_day_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.model.TripEvent
import com.smooth.travelplanner.domain.repository.*
import com.smooth.travelplanner.presentation.common.multi_fab.MultiFabItem
import com.smooth.travelplanner.util.toLongDateString
import com.smooth.travelplanner.util.toMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripDayDetailsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val mainRepository: BaseMainRepository,
    private val cachedMainRepository: BaseCachedMainRepository,
    private val tripsRepository: BaseTripsRepository,
    private val tripDaysRepository: BaseTripDaysRepository,
    private val tripEventsRepository: BaseTripEventsRepository
) : ViewModel() {
    val currentTripsWithSubCollectionsState = cachedMainRepository.tripsWithSubCollectionsState

    private val _tripDayDetailsData = MutableStateFlow(TripDayDetailsData())
    val tripDayDetailsData: StateFlow<TripDayDetailsData>
        get() = _tripDayDetailsData

    private val _tripDayState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val tripDayState: State<Response<Boolean>> = _tripDayState

    val fabItems = listOf(
        MultiFabItem(
            0,
            R.drawable.ic_confirm,
            "Save day"
        ),
        MultiFabItem(
            1,
            R.drawable.ic_add,
            "Add event"
        )
    )

    fun onDateChange(date: Date) {
        _tripDayDetailsData.value = _tripDayDetailsData.value.copy(
            date = date, dateLabel = "Date:  ${date.toLongDateString()}r."
        )
    }

    fun onFabSaveTripDayClicked(tripId: String, tripDayId: String) {
        if (tripDayId.isEmpty()) {
            addTripDay(tripId)
        } else {
            updateTripDay(tripId, tripDayId)
        }
        mainRepository.refreshData(user)
    }

    fun onDeleteDialogChange(tripEvent: TripEvent?) {
        _tripDayDetailsData.value = _tripDayDetailsData.value.copy(tripEventToBeDeleted = tripEvent)
        _tripDayDetailsData.value =
            _tripDayDetailsData.value.copy(deleteDialogState = !_tripDayDetailsData.value.deleteDialogState)
    }

    fun getCurrentTripOrNull(tripId: String): Trip? {
        return cachedMainRepository.getCurrentTripOrNull(tripId)
    }

    fun getCurrentTripDayOrNull(tripDayId: String): TripDay? {
        for (trip in (currentTripsWithSubCollectionsState.value as Response.Success).data) {
            for (tripDay in trip.tripDays) {
                if (tripDay.id == tripDayId) {
                    onDateChange(tripDay.date)
                    return tripDay
                }
            }
        }
        return null
    }

    private fun addTripDay(tripId: String) = viewModelScope.launch {
        val tripDay = TripDay(
            date = _tripDayDetailsData.value.date
        )
        tripDaysRepository.addTripDay(tripId, tripDay.toMap()).collect {
            _tripDayState.value = it
        }
    }

    private fun updateTripDay(tripId: String, tripDayId: String) = viewModelScope.launch {
        val tripDay = TripDay(
            date = _tripDayDetailsData.value.date
        )
        tripDaysRepository.updateTripDay(tripId, tripDayId, tripDay.toMap()).collect {
            _tripDayState.value = it
        }
    }

    fun deleteTripEvent(trip: Trip?, tripDay: TripDay?, tripEvent: TripEvent?) = viewModelScope.launch {
        if (trip != null && tripDay != null && tripEvent != null) {
            tripEventsRepository.deleteTripEvent(trip.id, tripDay.id, tripEvent.id).collect {
                _tripDayState.value = it
            }
            mainRepository.refreshData(user)
        }
    }

    data class TripDayDetailsData(
        val date: Date = Date(),
        val dateLabel: String = "Set date",
        val deleteDialogState: Boolean = false,
        val tripEventToBeDeleted: TripEvent? = null
    )
}