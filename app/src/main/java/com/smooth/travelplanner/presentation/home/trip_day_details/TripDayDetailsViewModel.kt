package com.smooth.travelplanner.presentation.home.trip_day_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.model.TripEvent
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import com.smooth.travelplanner.domain.repository.BaseMainRepository
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
import com.smooth.travelplanner.domain.repository.BaseTripEventsRepository
import com.smooth.travelplanner.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripDayDetailsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val mainRepository: BaseMainRepository,
    private val cachedMainRepository: BaseCachedMainRepository,
    private val tripDaysRepository: BaseTripDaysRepository,
    private val tripEventsRepository: BaseTripEventsRepository
) : ViewModel() {
    val currentTripsWithSubCollectionsState = cachedMainRepository.tripsWithSubCollectionsState

    private val _tripDayDetailsData = MutableStateFlow(TripDayDetailsData())
    val tripDayDetailsData = _tripDayDetailsData.asStateFlow()

    private val _deleteDialogData = MutableStateFlow(DeleteDialogData())
    val deleteDialogData = _deleteDialogData.asStateFlow()

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
        ),
        MultiFabItem(
            2,
            R.drawable.ic_close,
            "Delete day"
        )
    )

    init {
        mainRepository.refreshData(user)
    }

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

    fun onDeleteDialogChange(data: Any?) {
        when (data) {
            is TripEvent -> {
                _deleteDialogData.value = _deleteDialogData.value.copy(
                    tripEventToBeDeleted = data,
                    title = "Trip event deletion dialog",
                    description = "Do you want to delete \n${data.title}"
                )
            }
            is TripDay -> {
                _deleteDialogData.value = _deleteDialogData.value.copy(
                    tripDayToBeDeleted = data,
                    title = "Trip day deletion dialog",
                    description = "Do you want to delete \n${data.date.toLongDateString()}    ${data.date.toDayOfTheWeek()}?"
                )
            }
            else -> {
                if (data == null) {
                    _deleteDialogData.value = _deleteDialogData.value.copy(
                        tripDayToBeDeleted = data
                    )
                }
            }
        }
        _deleteDialogData.value =
            _deleteDialogData.value.copy(deleteDialogState = !_deleteDialogData.value.deleteDialogState)
    }

    fun getCurrentTripOrNull(tripId: String): Trip? {
        return cachedMainRepository.getCurrentTripOrNull(tripId)
    }

    fun getCurrentTripDayOrNull(tripDayId: String): TripDay? {
        val tripDay = cachedMainRepository.getCurrentTripDayOrNull(tripDayId)
        if (tripDay != null)
            onDateChange(tripDay.date)
        return tripDay
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

    fun deleteTripEvent(trip: Trip?, tripDay: TripDay?, tripEvent: TripEvent?) =
        viewModelScope.launch {
            if (trip != null && tripDay != null && tripEvent != null) {
                tripEventsRepository.deleteTripEvent(trip.id, tripDay.id, tripEvent.id).collect {
                    _tripDayState.value = it
                }
                mainRepository.refreshData(user)
            }
        }

    fun deleteTripDay(trip: Trip?, tripDay: TripDay?) = viewModelScope.launch {
        if (trip != null && tripDay != null) {
            tripDaysRepository.deleteTripDay(trip.id, tripDay.id).collect {
                _tripDayState.value = it
            }
            mainRepository.refreshData(user)
        }
    }

    data class TripDayDetailsData(
        val date: Date = Date(),
        val dateLabel: String = "Set date"
    )

    data class DeleteDialogData(
        val deleteDialogState: Boolean = false,
        val tripEventToBeDeleted: TripEvent? = null,
        val tripDayToBeDeleted: TripDay? = null,
        val title: String = "",
        val description: String = ""
    )
}