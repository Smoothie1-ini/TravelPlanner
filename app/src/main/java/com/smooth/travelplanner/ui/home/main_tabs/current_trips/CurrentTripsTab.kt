package com.smooth.travelplanner.ui.home.main_tabs.current_trips

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.ui.TabHeader
import com.smooth.travelplanner.ui.Trip
import com.smooth.travelplanner.ui.destinations.TripDetailsScreenDestination

@ExperimentalComposeUiApi
@Destination
@Composable
fun CurrentTripsTab(
    navigator: DestinationsNavigator,
    viewModel: CurrentTripsViewModel = hiltViewModel()
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TabHeader(
                text = "Ready for a new trippin adventure?",
                modifier = Modifier
            )
        }
        items(5) {
            Trip(
                onTripSelected = {
                    navigator.navigate(
                        direction = TripDetailsScreenDestination("CurrentTripsTab"),
                        onlyIfResumed = true
                    )
                },
                onTripDeleted = viewModel::deleteTrip,
            )
        }
    }
}