package com.smooth.travelplanner.ui.home.main_tabs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.expanding_fab.MultiFabItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(

) : ViewModel() {
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

    fun onFabSaveTripClicked() {
        Log.d("TripDetailsViewModel", "Save trip")
    }
}