package com.smooth.travelplanner.ui.home.main_tabs.current_trips

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.ui.TabHeader
import com.smooth.travelplanner.ui.Trip
import com.smooth.travelplanner.ui.destinations.TripScreenDestination

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
        items(10) {
            Trip(
                modifier = if (it == 10 - 1) Modifier.padding(bottom = 55.dp) else Modifier,
                onTripSelected = {
                    navigator.navigate(
                        direction = TripScreenDestination("x"),
                        onlyIfResumed = true
                    )
                },
                onTripDeleted = viewModel::deleteTrip,
            )
        }
    }
}