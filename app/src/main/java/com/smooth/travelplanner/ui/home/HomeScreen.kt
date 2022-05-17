package com.smooth.travelplanner.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.NavGraphs
import com.smooth.travelplanner.ui.destinations.ArchivedTripsTabDestination
import com.smooth.travelplanner.ui.destinations.CurrentTripsTabDestination
import com.smooth.travelplanner.ui.destinations.ProfileTabDestination
import com.smooth.travelplanner.ui.destinations.WishlistTabDestination
import kotlinx.coroutines.launch

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val iconId: Int,
    @StringRes val label: Int
) {
    CurrentTripsTab(
        CurrentTripsTabDestination,
        R.drawable.ic_home,
        R.string.current_trips_tab
    ),
    ArchivedTripsTab(
        ArchivedTripsTabDestination,
        R.drawable.ic_archive,
        R.string.archived_trips_tab
    ),
    WishlistTab(
        WishlistTabDestination,
        R.drawable.ic_favorite,
        R.string.wishlist_tab
    ),
    ProfileTab(
        ProfileTabDestination,
        R.drawable.ic_profile,
        R.string.profile_tab
    ),
}

@ExperimentalComposeUiApi
@Destination
@Composable
fun HomeScreen() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = MaterialTheme.colors.primary
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    Surface(color = MaterialTheme.colors.surface) {
        Image(
            painter = painterResource(id = R.drawable.bg_main),
            contentDescription = "Header background",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, -(30).dp)
        )
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            topBar = {
                TopBar {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            },
            bottomBar = {
                BottomBar(navController)
            },
            drawerContent = {
                Drawer()
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            when (scaffoldState.snackbarHostState.showSnackbar(
                                message = "Snack Bar",
                                actionLabel = "Dismiss"
                            )) {
                                SnackbarResult.Dismissed -> {

                                }
                                SnackbarResult.ActionPerformed -> {

                                }
                            }
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "FloatingButtonIcon",
                        tint = MaterialTheme.colors.background
                    )
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center
        ) {
            DestinationsNavHost(
                navController = navController,
                navGraph = NavGraphs.root,
                startRoute = CurrentTripsTabDestination
            )
        }
    }
}