package com.smooth.travelplanner.presentation.home.main_tabs.current_trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import com.smooth.travelplanner.domain.repository.BaseMainRepository
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentTripsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val mainRepository: BaseMainRepository,
    private val tripsRepository: BaseTripsRepository,
    cachedMainRepository: BaseCachedMainRepository
) : ViewModel() {
    val currentTripsWithSubCollections = cachedMainRepository.tripsWithSubCollectionsState

    private val _tripDetailsData = MutableStateFlow(CurrentTripsData())
    val tripDetailsData: StateFlow<CurrentTripsData>
        get() = _tripDetailsData

    init {
        mainRepository.refreshData(user)
    }

    fun onDeleteDialogChange(trip: Trip?) {
        _tripDetailsData.value = _tripDetailsData.value.copy(tripToBeDeleted = trip)
        _tripDetailsData.value =
            _tripDetailsData.value.copy(deleteDialogState = !_tripDetailsData.value.deleteDialogState)
    }

    fun deleteTrip(trip: Trip?) = viewModelScope.launch {
        if (trip != null) {
            tripsRepository.deleteTrip(trip.id).collect {
                //TODO store this state somewhere
            }
            mainRepository.refreshData(user)
        }
    }

    data class CurrentTripsData(
        val deleteDialogState: Boolean = false,
        val tripToBeDeleted: Trip? = null
    )
}