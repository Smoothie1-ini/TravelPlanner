package com.smooth.travelplanner.presentation.home.main_tabs.current_trips

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import com.smooth.travelplanner.domain.repository.BaseMainRepository
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentTripsViewModel @Inject constructor(
    private val user: FirebaseUser?,
    private val mainRepository: BaseMainRepository,
    cachedMainRepository: BaseCachedMainRepository,
    private val tripsRepository: BaseTripsRepository,
) : ViewModel() {
    val currentTripsWithSubCollectionsState = cachedMainRepository.tripsWithSubCollectionsState

    private val _tripDetailsData = MutableStateFlow(CurrentTripsData())
    val tripDetailsData = _tripDetailsData.asStateFlow()

    private val _tripState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val tripState: State<Response<Boolean>> = _tripState

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
                _tripState.value = it
            }
            mainRepository.refreshData(user)
        }
    }

    data class CurrentTripsData(
        val deleteDialogState: Boolean = false,
        val tripToBeDeleted: Trip? = null
    )
}