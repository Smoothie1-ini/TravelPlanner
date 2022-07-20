package com.smooth.travelplanner.presentation.home.trip_event_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.*
import com.smooth.travelplanner.domain.repository.*
import com.smooth.travelplanner.util.toHoursAndMinutes
import com.smooth.travelplanner.util.toMap
import com.smooth.travelplanner.util.toShortTimeString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripEventDetailsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val mainRepository: BaseMainRepository,
    private val cachedMainRepository: BaseCachedMainRepository,
    private val tripsRepository: BaseTripsRepository,
    private val tripDaysRepository: BaseTripDaysRepository,
    private val tripEventsRepository: BaseTripEventsRepository
) : ViewModel() {
    val currentTripsWithSubCollectionsState = cachedMainRepository.tripsWithSubCollectionsState

    private val _tripEventDetailsData = MutableStateFlow(TripEventDetailsData())
    val tripEventDetailsData = _tripEventDetailsData.asStateFlow()

    private val _deleteDialogData = MutableStateFlow(DeleteDialogData())
    val deleteDialogData = _deleteDialogData.asStateFlow()

    private val _tripEventState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val tripEventState: State<Response<Boolean>> = _tripEventState

    val fabItems = listOf(
        MultiFabItem(
            0,
            R.drawable.ic_confirm,
            "Save event"
        ),
        MultiFabItem(
            1,
            R.drawable.ic_add,
            "i dunno yet"
        ),
        MultiFabItem(
            2,
            R.drawable.ic_close,
            "Delete event"
        )
    )

    init {
        mainRepository.refreshData(user)
    }

    fun onPictureChange(picture: DocumentReference) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(picture = picture)
    }

    fun onTitleChange(title: String) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(description = description)
    }

    fun onTimeChange(date: Date) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(
            time = date, timeLabel = "Time:  ${date.toShortTimeString()}"
        )
    }

    fun onDurationHoursChange(durationHours: Int) {
        val durationMinutes = _tripEventDetailsData.value.duration.second
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(duration = Pair(durationHours, durationMinutes))
    }

    fun onDurationMinutesChange(durationMinutes: Int) {
        val durationHours = _tripEventDetailsData.value.duration.first
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(duration = Pair(durationHours, durationMinutes))
    }

    fun onCostChange(cost: String) {
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(cost = cost)
    }

    fun onRatingChange(rating: Int) {
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(rating = rating)
    }

    fun onFabSaveTripEventClicked(tripId: String, tripDayId: String, tripEventId: String) {
        if (tripEventId.isEmpty()) {
            addTripEvent(tripId, tripDayId)
        } else {
            updateTripEvent(tripId, tripDayId, tripEventId)
        }
        mainRepository.refreshData(user)
    }

    fun onDeleteDialogChange(tripEvent: TripEvent?) {
        _deleteDialogData.value = _deleteDialogData.value.copy(
            tripEventToBeDeleted = tripEvent,
            title = "Trip event deletion dialog",
            description = "Do you want to delete \n${tripEvent?.title}"
        )
        _deleteDialogData.value =
            _deleteDialogData.value.copy(deleteDialogState = !_deleteDialogData.value.deleteDialogState)
    }

    fun getCurrentTripOrNull(tripId: String): Trip? {
        return cachedMainRepository.getCurrentTripOrNull(tripId)
    }

    fun getCurrentTripDayOrNull(tripDayId: String): TripDay? {
        return cachedMainRepository.getCurrentTripDayOrNull(tripDayId)
    }

    fun getCurrentTripEventOrNull(tripEventId: String): TripEvent? {
        val tripEvent = cachedMainRepository.getCurrentTripEventOrNull(tripEventId)
        if (tripEvent != null) {
            onTitleChange(tripEvent.title)
            onDescriptionChange(tripEvent.description)
            onTimeChange(tripEvent.time)
            onDurationHoursChange(tripEvent.duration.toHoursAndMinutes().first)
            onDurationMinutesChange(tripEvent.duration.toHoursAndMinutes().second)
            onRatingChange(tripEvent.rating)
            onCostChange(tripEvent.cost)
        }
        return tripEvent
    }

    private fun addTripEvent(tripId: String, tripDayId: String) = viewModelScope.launch {
        val tripEvent = TripEvent(
            title = _tripEventDetailsData.value.title,
            description = _tripEventDetailsData.value.description,
            time = _tripEventDetailsData.value.time,
            duration = 60 * _tripEventDetailsData.value.duration.first + _tripEventDetailsData.value.duration.second,
            //location = _tripEventDetailsData.value.location,
            cost = _tripEventDetailsData.value.cost,
            rating = _tripEventDetailsData.value.rating,
            //picture = _tripEventDetailsData.value.picture
        )
        tripEventsRepository.addTripEvent(tripId, tripDayId, tripEvent.toMap()).collect {
            _tripEventState.value = it
        }
    }

    private fun updateTripEvent(tripId: String, tripDayId: String, tripEventId: String) =
        viewModelScope.launch {
            val tripEvent = TripEvent(
                title = _tripEventDetailsData.value.title,
                description = _tripEventDetailsData.value.description,
                time = _tripEventDetailsData.value.time,
                duration = 60 * _tripEventDetailsData.value.duration.first + _tripEventDetailsData.value.duration.second,
                //location = _tripEventDetailsData.value.location,
                cost = _tripEventDetailsData.value.cost,
                rating = _tripEventDetailsData.value.rating,
                //picture = _tripEventDetailsData.value.picture
            )
            tripEventsRepository.updateTripEvent(tripId, tripDayId, tripEventId, tripEvent.toMap())
                .collect {
                    _tripEventState.value = it
                }
        }

    fun deleteTripEvent(trip: Trip?, tripDay: TripDay?, tripEvent: TripEvent?) =
        viewModelScope.launch {
            if (trip != null && tripDay != null && tripEvent != null) {
                tripEventsRepository.deleteTripEvent(trip.id, tripDay.id, tripEvent.id).collect {
                    _tripEventState.value = it
                }
                mainRepository.refreshData(user)
            }
        }

    data class TripEventDetailsData(
        val title: String = "",
        val description: String = "",
        val time: Date = Date(),
        val timeLabel: String = "Set time\nand duration",
        val duration: Pair<Int, Int> = Pair(0, 0),
        val cost: String = "",
        val rating: Int = 0,
        val location: GeoPoint? = null,
        val picture: DocumentReference? = null
    )

    data class DeleteDialogData(
        val deleteDialogState: Boolean = false,
        val tripEventToBeDeleted: TripEvent? = null,
        val title: String = "",
        val description: String = ""
    )
}