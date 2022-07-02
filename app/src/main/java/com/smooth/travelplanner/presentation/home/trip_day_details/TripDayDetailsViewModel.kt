package com.smooth.travelplanner.presentation.home.trip_day_details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import com.smooth.travelplanner.domain.repository.BaseMainRepository
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import com.smooth.travelplanner.presentation.common.multi_fab.MultiFabItem
import com.smooth.travelplanner.util.toLongDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TripDayDetailsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val mainRepository: BaseMainRepository,
    cachedMainRepository: BaseCachedMainRepository,
    private val tripsRepository: BaseTripsRepository,
    private val tripDaysRepository: BaseTripDaysRepository
) : ViewModel() {
    val currentTripsWithSubCollectionsState = cachedMainRepository.tripsWithSubCollectionsState

    private val _tripDayDetailsData = MutableStateFlow(TripDayDetailsData())
    val tripDayDetailsData: StateFlow<TripDayDetailsData>
        get() = _tripDayDetailsData

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

    fun onFabSaveTripDayClicked() {
        Log.d("TripDayDetailsViewModel", "Save day")
    }

    fun getCurrentTripDayOrNull(tripDayId: String): TripDay? {
        for (trip in (currentTripsWithSubCollectionsState.value as Response.Success).data) {
            for (tripDay in trip.tripDays) {
                if (tripDay.id == tripDayId) {
                    if (tripDay.date != null)
                        onDateChange(tripDay.date)
                    return tripDay
                }
            }
        }
        return null
    }

    fun deleteTripEvent() {
        //TODO trip deletion
    }

    data class TripDayDetailsData(
        val date: Date = Date(),
        val dateLabel: String = "Set date"
    )
}