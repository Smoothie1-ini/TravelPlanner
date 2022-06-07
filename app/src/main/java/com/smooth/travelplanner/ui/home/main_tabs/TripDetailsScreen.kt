package com.smooth.travelplanner.ui.home.main_tabs

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.MyStyledTextField
import com.smooth.travelplanner.ui.home.main_tabs.current_trips.TripDay
import com.smooth.travelplanner.ui.multi_fab.FabIcon
import com.smooth.travelplanner.ui.multi_fab.MultiFloatingActionButton
import com.smooth.travelplanner.ui.multi_fab.fabOption
import com.smooth.travelplanner.ui.multi_fab.rememberMultiFabState

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun TripDetailsScreen(
    tripId: String = "",
    viewModel: TripDetailsViewModel = hiltViewModel()
) {
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
            floatingActionButton =
            {
                MultiFloatingActionButton(
                    items = viewModel.fabItems,
                    fabState = multiFabState,
                    fabIcon = FabIcon(R.drawable.ic_add, 135f),
                    onFabItemClicked = {
                        if (it.id == 0)
                            viewModel.onFabSaveTripClicked()
                        else
                            Log.d("TripDetailsScreen", "navigate to DayDetailsScreen")
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
                    MyStyledTextField(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        fontSize = 26,
                        maxLines = 1,
                        hint = "Title $tripId"
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    MyStyledTextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        textAlign = TextAlign.Center,
                        fontSize = 16,
                        maxLines = 4,
                        hint = "Description"
                    )
                }
                items(10) {
                    TripDay()
                }
            }
        }
    }
}