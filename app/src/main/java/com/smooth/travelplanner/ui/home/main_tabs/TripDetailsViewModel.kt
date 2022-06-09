package com.smooth.travelplanner.ui.home.main_tabs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.common.multi_fab.MultiFabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(

) : ViewModel() {
    private val _tripDetailsData = MutableStateFlow(TripDetailsData())
    val tripDetailsData: StateFlow<TripDetailsData>
        get() = _tripDetailsData

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

    fun onFabSaveTripClicked() {
        Log.d("TripDetailsViewModel", "Save trip")
    }

    fun deleteTripDay() {
        //TODO trip deletion
    }

    data class TripDetailsData(
        val title: String = "",
        val description: String = ""
    )
}