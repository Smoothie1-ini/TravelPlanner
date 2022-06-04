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
import com.smooth.travelplanner.ui.MyStyledTextField

@ExperimentalComposeUiApi
@Destination
@Composable
fun TripDetailsScreen(
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

    Surface {
        Scaffold(
            backgroundColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {

                    },
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.offset(x = (-10).dp, y = (-10).dp),
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