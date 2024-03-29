package com.smooth.travelplanner.presentation.home.main_tabs.current_trips

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.smooth.travelplanner.presentation.common.ProgressBar
import com.smooth.travelplanner.presentation.common.TabHeader
import com.smooth.travelplanner.presentation.destinations.TripDetailsScreenDestination
import com.smooth.travelplanner.presentation.home.ConfirmCancelDialog
import com.smooth.travelplanner.presentation.home.main_tabs.Trip
import com.smooth.travelplanner.util.Response

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun CurrentTripsTab(
    homeScreenNavController: NavController,
    viewModel: CurrentTripsViewModel = hiltViewModel()
) {
    val currentTripsData = viewModel.tripDetailsData.collectAsState()

    Surface(
        color = Color.Transparent
    ) {
        ConfirmCancelDialog(
            visible = currentTripsData.value.deleteDialogState,
            onValueChanged = {
                if (it) viewModel.deleteTrip(currentTripsData.value.tripToBeDeleted)
                viewModel.onDeleteDialogChange(null)
            },
            title = "Trip deletion dialog",
            text = "Do you want to delete ${currentTripsData.value.tripToBeDeleted?.title}?"
        )
        when (val tripsResponse =
            viewModel.currentTripsWithSubCollectionsState.collectAsState().value) {
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
                //TODO fix to progressIndicate single item
                items(tripsResponse.data.filter {
                    !it.isArchived
                }) { trip ->
                    when (val tripResponse = viewModel.tripState.value) {
                        is Response.Loading -> CircularProgressIndicator()
                        is Response.Success -> {
                            Trip(
                                modifier = if (tripsResponse.data.last() == trip) Modifier.padding(
                                    bottom = 20.dp
                                ) else Modifier,
                                onTripSelect = {
                                    homeScreenNavController.navigate(
                                        direction = TripDetailsScreenDestination(trip.id),
                                        navOptionsBuilder = {
                                            launchSingleTop = true
                                        }
                                    )
                                },
                                onDeleteDialogChange = {
                                    viewModel.onDeleteDialogChange(trip)
                                },
                                trip = trip
                            )
                        }
                        is Response.Error ->
                            Log.d("CurrentTripsTab", tripResponse.message)
                        is Response.Message ->
                            Log.d("CurrentTripsTab", tripResponse.message)
                    }
                }
            }
            is Response.Error ->
                Log.d("CurrentTripsTab", tripsResponse.message)
            is Response.Message ->
                Log.d("CurrentTripsTab", tripsResponse.message)
        }
    }
}
