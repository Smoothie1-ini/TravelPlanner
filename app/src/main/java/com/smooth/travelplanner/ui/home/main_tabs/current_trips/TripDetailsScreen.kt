package com.smooth.travelplanner.ui.home.main_tabs.current_trips

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.ui.MyStyledTextField

@ExperimentalComposeUiApi
@Destination
@Composable
fun TripDetailsScreen(
    navigator: DestinationsNavigator,
    tripId: String = ""
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

    Surface(
        color = MaterialTheme.colors.surface
    ) {
        Scaffold(
            backgroundColor = Color.Transparent,
//            topBar = {
//                Row(
//                    Modifier
//                        .padding(16.dp)
//                        .height(54.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    IconButton(
//                        onClick = {
//                            //TODO navigate back
//                            navigator.popBackStack()
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_back),
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    //TODO properly implement state for searchbar
//                    SearchBar(
//                        modifier = Modifier.weight(1f),
//                        value = "",
//                        onValueChanged = {}
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    IconButton(
//                        onClick = {
//                            //TODO save a day
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_confirm),
//                            contentDescription = "",
//                            tint = Color.White
//                        )
//                    }
//                }
//            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {

                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.offset(x = (-30).dp, y = (-30).dp),
                    elevation = FloatingActionButtonDefaults.elevation(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "FloatingButtonIcon",
                        tint = MaterialTheme.colors.background
                    )
                }
            },
            isFloatingActionButtonDocked = false,
            floatingActionButtonPosition = FabPosition.End
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize(),
                    //.padding(top = 20.dp),
                contentPadding = it
            ) {
                item {
                    MyStyledTextField(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Center,
                        fontSize = 26,
                        maxLines = 1,
                        hint = "Title"
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
                items(20) {
                    TripDay()
                }
            }
        }
    }
}