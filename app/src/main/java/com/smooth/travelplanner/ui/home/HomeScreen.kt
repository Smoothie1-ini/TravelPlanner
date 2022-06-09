package com.smooth.travelplanner.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigateTo
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.NavGraphs
import com.smooth.travelplanner.ui.destinations.CurrentTripsTabDestination
import com.smooth.travelplanner.ui.destinations.TripDetailsScreenDestination
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val homeData = viewModel.homeData.collectAsState()
    val homeScreenNavController = rememberNavController()

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val topBarCoroutineScope = rememberCoroutineScope()

    val navBackStackEntry by homeScreenNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore('?')
    val topBarState = currentRoute != "profile_tab"
    viewModel.onTopBarChanged(topBarState)
    val bottomBarState =
        !arrayOf(
            "trip_details_screen",
            "trip_day_details_screen",
            "trip_event_details_screen"
        ).any { it == currentRoute }
    viewModel.onBottomBarChanged(bottomBarState)

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    val color = MaterialTheme.colors.primary
    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    BackHandler(enabled = true) {
        viewModel.onLogOutDialogChanged()
    }

    Surface(color = MaterialTheme.colors.surface) {
        ConfirmCancelDialog(
            visible = homeData.value.logOutDialogState,
            onValueChanged = {
                viewModel.onLogOutDialogChanged()
                if (it) {
                    viewModel.signOut()
                    Toast.makeText(
                        context,
                        viewModel.currentUser.value?.email.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigator.popBackStack()
                }
            },
            title = "Log out",
            text = "Do you want to log out?"
        )
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
                TopBar(
                    visible = topBarState,
                    openDrawer = {
                        topBarCoroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    searchBarValue = homeData.value.searchBarValue,
                    onSearchBarValueChanged = viewModel::onSearchBarValueChanged
                )
            },
            bottomBar = {
                if (bottomBarState)
                    BottomBar(homeScreenNavController)
            },
            drawerContent = {
                Drawer()
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        homeScreenNavController.navigateTo(TripDetailsScreenDestination("HomeScreen")) {
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
            Column(modifier = Modifier.padding(it)) {
                DestinationsNavHost(
                    navController = homeScreenNavController,
                    navGraph = NavGraphs.root,
                    startRoute = CurrentTripsTabDestination
                )
            }
        }
    }
}