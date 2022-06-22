package com.smooth.travelplanner.presentation.home.trip_day_details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.smooth.travelplanner.R
import com.smooth.travelplanner.presentation.common.multi_fab.MultiFabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TripDayDetailsViewModel @Inject constructor(

) : ViewModel() {
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

    fun onDateChange(date: LocalDate) {
        _tripDayDetailsData.value = _tripDayDetailsData.value.copy(
            date = date, dateLabel = "Date:  ${date.format(DateTimeFormatter.ISO_LOCAL_DATE)}r."
        )
    }

    fun onFabSaveTripDayClicked() {
        Log.d("TripDayDetailsViewModel", "Save day")
    }

    fun deleteTripEvent() {
        //TODO trip deletion
    }

    data class TripDayDetailsData(
        val date: LocalDate = LocalDate.now(),
        val dateLabel: String = "Set date"
    )
}