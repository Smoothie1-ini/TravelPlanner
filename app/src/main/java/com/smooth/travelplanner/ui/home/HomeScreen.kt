package com.smooth.travelplanner.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
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
            bottomBar = {
                BottomBar()
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
                TopBar()
                EmptySection()
                Content()
            }
        }
    }
}