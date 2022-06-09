package com.smooth.travelplanner.ui.home.main_tabs

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.smooth.travelplanner.ui.common.DatePickerBar
import com.smooth.travelplanner.ui.common.TripEvent
import com.smooth.travelplanner.ui.common.multi_fab.FabIcon
import com.smooth.travelplanner.ui.common.multi_fab.MultiFloatingActionButton
import com.smooth.travelplanner.ui.common.multi_fab.fabOption
import com.smooth.travelplanner.ui.common.multi_fab.rememberMultiFabState
import com.smooth.travelplanner.ui.destinations.TripEventDetailsScreenDestination

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun TripDayDetailsScreen(
    tripDayId: String = "",
    homeScreenNavController: NavController,
    viewModel: TripDayDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
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
                                viewModel.onFabSaveTripDayClicked()
                            }
                            1 -> {
                                homeScreenNavController.navigateTo(
                                    TripEventDetailsScreenDestination()
                                ) {
                                    launchSingleTop = true
                                }
                                Log.d("TripDayDetailsScreen", "navigate to TripEventDetailsScreen")
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
            LazyColumn(
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
                        value = tripDayDetailsData.value.date,
                        onValueChange = viewModel::onDateChange
                    )
                }
                items(count = 7) {
                    TripEvent(
                        onTripEventSelect = {
                            homeScreenNavController.navigateTo(TripEventDetailsScreenDestination("5")) {
                                launchSingleTop = true
                            }
                        },
                        onTripEventDelete = viewModel::deleteTripEvent,
                        onTripEventFavorite = {
                            //TODO add/delete to/from wishlist
                        },
                        onTripEventNavigate = {
                            //TODO map intent
                        }
                    )
                }
            }
        }
    }
}