package com.smooth.travelplanner.presentation.home.trip_details

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.presentation.common.MyStyledTextField
import com.smooth.travelplanner.presentation.common.ProgressBar
import com.smooth.travelplanner.presentation.common.TripDay
import com.smooth.travelplanner.presentation.common.multi_fab.FabIcon
import com.smooth.travelplanner.presentation.common.multi_fab.MultiFloatingActionButton
import com.smooth.travelplanner.presentation.common.multi_fab.fabOption
import com.smooth.travelplanner.presentation.common.multi_fab.rememberMultiFabState
import com.smooth.travelplanner.presentation.destinations.TripDayDetailsScreenDestination
import com.smooth.travelplanner.presentation.home.ConfirmCancelDialog
import com.smooth.travelplanner.util.toDayOfTheWeek
import com.smooth.travelplanner.util.toLongDateString

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun TripDetailsScreen(
    tripId: String = "",
    homeScreenNavController: NavController,
    viewModel: TripDetailsViewModel = hiltViewModel()
) {
    val currentTrip = viewModel.getCurrentTripOrNull(tripId)
    val currentTripDetailsData = viewModel.tripDetailsData.collectAsState()

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = MaterialTheme.colors.primary
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    val multiFabState = rememberMultiFabState()

    Surface {
        ConfirmCancelDialog(
            visible = currentTripDetailsData.value.deleteDialogState,
            onValueChanged = {
                if (it) viewModel.deleteTripDay(
                    currentTrip,
                    currentTripDetailsData.value.tripDayToBeDeleted
                )
                viewModel.onDeleteDialogChange(null)
            },
            title = "Trip day deletion dialog",
            text = "Do you want to delete \n${currentTripDetailsData.value.tripDayToBeDeleted?.date?.toLongDateString()}    ${currentTripDetailsData.value.tripDayToBeDeleted?.date?.toDayOfTheWeek()}?"
        )
        Scaffold(
            backgroundColor = Color.Transparent,
            floatingActionButton = {
                MultiFloatingActionButton(
                    items = viewModel.fabItems,
                    fabState = multiFabState,
                    fabIcon = FabIcon(R.drawable.ic_edit, 260f),
                    onFabItemClicked = {
                        when (it.id) {
                            0 -> {
                                viewModel.onFabSaveTripClicked(tripId)
                            }
                            1 -> {
                                homeScreenNavController.navigateTo(
                                    TripDayDetailsScreenDestination(
                                        tripId
                                    )
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    fabOption = fabOption(
                        backgroundTint = MaterialTheme.colors.primaryVariant,
                        iconTint = MaterialTheme.colors.background,
                        showLabel = true
                    )
                )
            },
            isFloatingActionButtonDocked = false,
            floatingActionButtonPosition = FabPosition.End
        ) {
            when (val tripsResponse =
                viewModel.currentTripsWithSubCollectionsState.collectAsState().value) {
                is Response.Loading -> ProgressBar()
                is Response.Success -> LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = it
                ) {
                    item {
                        MyStyledTextField(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardType = KeyboardType.Text,
                            textAlign = TextAlign.Center,
                            fontSize = 26,
                            maxLines = 1,
                            hint = "Title",
                            value = currentTripDetailsData.value.title,
                            onValueChange = viewModel::onTitleChange
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        MyStyledTextField(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            keyboardType = KeyboardType.Text,
                            textAlign = TextAlign.Center,
                            fontSize = 16,
                            maxLines = 4,
                            hint = "Description",
                            value = currentTripDetailsData.value.description,
                            onValueChange = viewModel::onDescriptionChange
                        )
                    }
                    //TODO recomposition is not working on state change, items are populated from an out of date list
                    items(currentTrip?.tripDays ?: listOf()) { tripDay ->
                        TripDay(
                            onTripDaySelect = {
                                homeScreenNavController.navigateTo(
                                    direction = TripDayDetailsScreenDestination(tripId, tripDay.id),
                                    navOptionsBuilder = {
                                        launchSingleTop = true
                                    }
                                )
                            },
                            onTripDayDelete = {
                                viewModel.onDeleteDialogChange(tripDay)
                            },
                            tripDay = tripDay
                        )
                    }
                }
                is Response.Error ->
                    Log.d("CurrentTripsTab", tripsResponse.message)
                is Response.Message ->
                    Log.d("CurrentTripsTab", tripsResponse.message)
            }
        }
    }
}