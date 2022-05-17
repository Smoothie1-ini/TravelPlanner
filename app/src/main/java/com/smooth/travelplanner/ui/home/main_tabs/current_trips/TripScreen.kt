package com.smooth.travelplanner.ui.home.main_tabs.current_trips

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.smooth.travelplanner.R
import com.smooth.travelplanner.ui.MyStyledTextField
import com.smooth.travelplanner.ui.home.SearchBar

@ExperimentalComposeUiApi
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun NewTripScreen(
    navigator: DestinationsNavigator
) {
    Surface(
        color = MaterialTheme.colors.surface
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_main),
            contentDescription = "Header background",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, -(55).dp)
        )
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                Row(
                    Modifier
                        .padding(16.dp)
                        .height(54.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {
                            //TODO navigate back
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    SearchBar(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            //TODO save a day
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_confirm),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            },
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                MyStyledTextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    textAlign = TextAlign.Center,
                    fontSize = 26,
                    maxLines = 1,
                    hint = "Title"
                )
                Spacer(modifier = Modifier.height(10.dp))
                MyStyledTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16,
                    maxLines = 4,
                    hint = "Description"
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(20) {
                        TripDay()
                    }
                }
            }
        }
    }
}