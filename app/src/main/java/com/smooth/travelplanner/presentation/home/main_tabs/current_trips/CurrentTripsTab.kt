package com.smooth.travelplanner.presentation.home.main_tabs.current_trips

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.presentation.common.ProgressBar
import com.smooth.travelplanner.presentation.common.TabHeader
import com.smooth.travelplanner.presentation.common.Trip
import com.smooth.travelplanner.presentation.destinations.TripDetailsScreenDestination

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun CurrentTripsTab(
    homeScreenNavController: NavController,
    viewModel: CurrentTripsViewModel = hiltViewModel()
) {
    when (val tripsResponse = viewModel.tripsState.value) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                TabHeader(
                    text = "Ready for a new trippin adventure?",
                    modifier = Modifier
                )
            }
            items(tripsResponse.data) {
                Trip(
                    onTripSelect = {
                        homeScreenNavController.navigateTo(
                            direction = TripDetailsScreenDestination(it.id),
                            navOptionsBuilder = {
                                launchSingleTop = true
                            }
                        )
                    },
                    onTripDelete = {
                      viewModel.deleteTrip(it)
                    },
                    trip = it
                )
            }
        }
        is Response.Error ->
            Log.d("CurrentTripsTab", tripsResponse.message)
        is Response.Message ->
            Log.d("CurrentTripsTab", tripsResponse.message)
    }
}
