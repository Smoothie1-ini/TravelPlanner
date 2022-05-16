package com.smooth.travelplanner.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.home.main_tabs.archived_trips.ArchivedTripsTab
import com.smooth.travelplanner.ui.home.main_tabs.current_trips.CurrentTripsTab
import com.smooth.travelplanner.ui.home.main_tabs.profile.ProfileTab
import com.smooth.travelplanner.ui.home.main_tabs.wishlist.WishlistTab
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

    var selectedTabIndex by remember {
        mutableStateOf(0)
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = useDarkIcons
        )
    }

    Surface(color = MaterialTheme.colors.surface) {
        if (selectedTabIndex in (0..2)) {
            Image(
                painter = painterResource(id = R.drawable.bg_main),
                contentDescription = "Header background",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(0.dp, -(30).dp)
            )
        }
        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent,
            bottomBar = {
                BottomAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.primaryVariant,
                    cutoutShape = MaterialTheme.shapes.small.copy(
                        CornerSize(percent = 50)
                    )
                ) {
                    BottomBar(
                        iconIds = listOf(
                            R.drawable.ic_home,
                            R.drawable.ic_archive,
                            R.drawable.ic_favorite,
                            R.drawable.ic_account
                        )
                    ) {
                        selectedTabIndex = it
                    }
                }
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                when (selectedTabIndex) {
                    0 -> {
                        TopBar {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                        EmptySection()
                        CurrentTripsTab()
                    }
                    1 -> {
                        TopBar {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                        EmptySection()
                        ArchivedTripsTab()
                    }
                    2 -> {
                        TopBar {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                        WishlistTab()
                    }
                    3 -> {
                        ProfileTab()
                    }
                }
            }
        }
    }
}