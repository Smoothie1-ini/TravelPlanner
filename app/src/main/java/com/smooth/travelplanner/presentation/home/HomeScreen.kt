package com.smooth.travelplanner.presentation.home

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.smooth.travelplanner.R
import com.smooth.travelplanner.presentation.NavGraphs
import com.smooth.travelplanner.presentation.destinations.*
import com.smooth.travelplanner.util.Constants.PROFILE_TAB
import com.smooth.travelplanner.util.Constants.TRIP_DAY_DETAILS_SCREEN
import com.smooth.travelplanner.util.Constants.TRIP_DETAILS_SCREEN
import com.smooth.travelplanner.util.Constants.TRIP_EVENT_DETAILS_SCREEN
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeData = viewModel.homeData.collectAsState()

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val topBarCoroutineScope = rememberCoroutineScope()

    val homeScreenNavController = rememberNavController()
    val navBackStackEntry by homeScreenNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore('?')
    val topBarState = currentRoute != PROFILE_TAB
    viewModel.onTopBarChange(topBarState)
    val bottomBarState =
        !arrayOf(
            TRIP_DETAILS_SCREEN,
            TRIP_DAY_DETAILS_SCREEN,
            TRIP_EVENT_DETAILS_SCREEN
        ).any { it == currentRoute }
    viewModel.onBottomBarChange(bottomBarState)

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
        viewModel.onLogOutDialogChange()
    }

    Surface(color = MaterialTheme.colors.surface) {
        ConfirmCancelDialog(
            visible = homeData.value.logOutDialogState,
            onValueChanged = {
                viewModel.onLogOutDialogChange()
                if (it) {
                    viewModel.signOut()
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
                    onSearchBarValueChanged = viewModel::onSearchBarValueChange
                )
            },
            bottomBar = {
                if (bottomBarState)
                    BottomBar(homeScreenNavController)
            },
            drawerContent = {
                Drawer(
                    onLogoutClick = {
                        viewModel.onLogOutDialogChange()
                    },
                    onSettingsClick = { },
                    onCurrentClick = {
                        homeScreenNavController.navigate(CurrentTripsTabDestination())
                        topBarCoroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    onArchivedClick = {
                        homeScreenNavController.navigate(ArchivedTripsTabDestination())
                        topBarCoroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    onWishlistClick = {
                        homeScreenNavController.navigate(WishlistTabDestination())
                        topBarCoroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    onProfileClick = {
                        homeScreenNavController.navigate(ProfileTabDestination())
                        topBarCoroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    onAboutClick = { },
                    onFeedbackClick = { },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        homeScreenNavController.navigate(TripDetailsScreenDestination()) {
                            launchSingleTop = true
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    modifier = if (navBackStackEntry?.destination?.route != "current_trips_tab") Modifier.offset(
                        x = 0.dp,
                        y = 100.dp
                    ) else Modifier,
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