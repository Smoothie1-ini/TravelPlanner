package com.smooth.travelplanner.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.NavGraphs
import com.smooth.travelplanner.ui.destinations.CurrentTripsTabDestination
import com.smooth.travelplanner.ui.destinations.TripScreenDestination
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = MaterialTheme.colors.primary
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val topBarState = rememberSaveable {
        mutableStateOf(true)
    }
    topBarState.value = navBackStackEntry?.destination?.route != "profile_tab"

//    val bottomBarState = rememberSaveable {
//        mutableStateOf(true)
//    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

//    when (navBackStackEntry?.destination?.route) {
//        "current_trips_tab" -> {
//            // Show BottomBar and TopBar
//            bottomBarState.value = true
//            topBarState.value = true
//        }
//        "archived_trips_tab" -> {
//            // Show BottomBar and TopBar
//            bottomBarState.value = true
//            topBarState.value = true
//        }
//        "wishlist_tab" -> {
//            // Show BottomBar and TopBar
//            bottomBarState.value = true
//            topBarState.value = true
//        }
//        "profile_tab" -> {
//            // Hide BottomBar and TopBar
//            bottomBarState.value = false
//            topBarState.value = false
//        }
//    }

//    coroutineScope.launch {
//        when (scaffoldState.snackbarHostState.showSnackbar(
//            message = "Snack Bar",
//            actionLabel = "Dismiss"
//        )) {
//            SnackbarResult.Dismissed -> {
//
//            }
//            SnackbarResult.ActionPerformed -> {
//
//            }
//        }
//    }

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
                TopBar(topBarState) {
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
                        navigator.navigate(TripScreenDestination) {
                            launchSingleTop = true
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    modifier = if (navBackStackEntry?.destination?.route != "current_trips_tab") Modifier.offset(
                        x = 0.dp,
                        y = 100.dp
                    ) else Modifier.offset(
                        x = 0.dp,
                        y = (-5).dp
                    ),
                    elevation = FloatingActionButtonDefaults.elevation(5.dp)
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