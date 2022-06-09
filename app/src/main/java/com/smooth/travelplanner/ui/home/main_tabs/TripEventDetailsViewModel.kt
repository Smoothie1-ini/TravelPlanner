package com.smooth.travelplanner.ui.home.main_tabs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.common.multi_fab.MultiFabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TripEventDetailsViewModel @Inject constructor(

) : ViewModel() {
    private val _tripEventDetailsData = MutableStateFlow(TripEventDetailsData())
    val tripEventDetailsData: StateFlow<TripEventDetailsData>
        get() = _tripEventDetailsData

    val fabItems = listOf(
        MultiFabItem(
            0,
            R.drawable.ic_confirm,
            "Save event"
        ),
        MultiFabItem(
            1,
            R.drawable.ic_add,
            "i don't know yet"
        )
    )

    fun onImageChange(image: String) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(image = image)
    }

    fun onTitleChange(title: String) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(description = description)
    }

    fun onTimeChange(time: LocalTime) {
        _tripEventDetailsData.value = _tripEventDetailsData.value.copy(
            time = time, timeLabel = "Time:  ${time.format(DateTimeFormatter.ofPattern("HH : mm"))}"
        )
    }

    fun onDurationHoursChange(durationHours: Int) {
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(durationHours = durationHours)
    }

    fun onDurationMinutesChange(durationMinutes: Int) {
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(durationMinutes = durationMinutes)
    }

    fun onCostChange(cost: String) {
        var formattedCost: String? = cost.replace(',', '.')
        if (formattedCost?.count { it == '.' }!! > 1) return
        if (formattedCost.toFloat() == 0f)
            formattedCost = null
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(cost = formattedCost?.toFloat())
    }

    fun onRatingChange(rating: Float) {
        _tripEventDetailsData.value =
            _tripEventDetailsData.value.copy(rating = rating)
    }

    fun onFabSaveTripEventClicked() {
        Log.d("TripDayDetailsViewModel", "Save day")
    }

    data class TripEventDetailsData(
        val image: String = "",
        val title: String = "",
        val description: String = "",
        val time: LocalTime = LocalTime.now(),
        val timeLabel: String = "Set time\nand duration",
        val durationHours: Int = 0,
        val durationMinutes: Int = 0,
        val cost: Float? = null,
        val rating: Float = 0f
    )
}