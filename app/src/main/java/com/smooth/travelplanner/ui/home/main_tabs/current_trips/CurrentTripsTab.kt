package com.smooth.travelplanner.ui.home.main_tabs.current_trips

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import com.smooth.travelplanner.ui.common.TabHeader
import com.smooth.travelplanner.ui.common.Trip
import com.smooth.travelplanner.ui.destinations.TripDetailsScreenDestination

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun CurrentTripsTab(
    homeScreenNavController: NavController,
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
                onTripSelect = {
                    homeScreenNavController.navigateTo(
                        direction = TripDetailsScreenDestination("CurrentTripsTab"),
                        navOptionsBuilder = {
                            launchSingleTop = true
                        }
                    )
                },
                onTripDelete = viewModel::deleteTrip,
            )
        }
    }
}