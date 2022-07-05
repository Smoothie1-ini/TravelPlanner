package com.smooth.travelplanner.presentation.home.trip_day_details

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import com.smooth.travelplanner.R
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.presentation.common.DatePickerBar
import com.smooth.travelplanner.presentation.common.ProgressBar
import com.smooth.travelplanner.presentation.common.TripEvent
import com.smooth.travelplanner.presentation.common.multi_fab.FabIcon
import com.smooth.travelplanner.presentation.common.multi_fab.MultiFloatingActionButton
import com.smooth.travelplanner.presentation.common.multi_fab.fabOption
import com.smooth.travelplanner.presentation.common.multi_fab.rememberMultiFabState
import com.smooth.travelplanner.presentation.destinations.TripEventDetailsScreenDestination
import com.smooth.travelplanner.presentation.home.ConfirmCancelDialog
import com.smooth.travelplanner.util.toDayOfTheWeek
import com.smooth.travelplanner.util.toLongDateString
import java.time.ZoneId
import java.util.*

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun TripDayDetailsScreen(
    tripId: String = "",
    tripDayId: String = "",
    homeScreenNavController: NavController,
    viewModel: TripDayDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val currentTrip = viewModel.getCurrentTripOrNull(tripId)
    val currentTripDay = viewModel.getCurrentTripDayOrNull(tripDayId)
    val tripDayDetailsData = viewModel.tripDayDetailsData.collectAsState()

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
            visible = tripDayDetailsData.value.deleteDialogState,
            onValueChanged = {
                if (it) viewModel.deleteTripEvent(
                    currentTrip,
                    currentTripDay,
                    tripDayDetailsData.value.tripEventToBeDeleted
                )
                viewModel.onDeleteDialogChange(null)
            },
            title = "Trip event deletion dialog",
            text = "Do you want to delete \n${tripDayDetailsData.value.tripEventToBeDeleted?.title}?"
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
                                viewModel.onFabSaveTripDayClicked(tripId, tripDayId)
                            }
                            1 -> {
                                homeScreenNavController.navigateTo(
                                    TripEventDetailsScreenDestination()
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
                        DatePickerBar(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .padding(vertical = 15.dp),
                            context = context,
                            label = tripDayDetailsData.value.dateLabel,
                            value = tripDayDetailsData.value.date.toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate(),
                            onValueChange = { date ->
                                viewModel.onDateChange(
                                    Date.from(
                                        date.atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    )
                                )
                            }
                        )
                    }
                    items(currentTripDay?.tripEvents ?: listOf()) { tripEvent ->
                        TripEvent(
                            onTripEventSelect = {
                                homeScreenNavController.navigateTo(
                                    TripEventDetailsScreenDestination(tripEvent.id)
                                ) {
                                    launchSingleTop = true
                                }
                            },
                            onTripEventDelete = {
                                viewModel.onDeleteDialogChange(tripEvent)
                            },
                            onTripEventFavorite = {
                                //TODO add/delete to/from wishlist
                            },
                            onTripEventNavigate = {
                                //TODO map intent
                            },
                            tripEvent = tripEvent
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