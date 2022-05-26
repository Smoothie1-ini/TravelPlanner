package com.smooth.travelplanner.ui.home.main_tabs.archived_trips

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.ui.TabHeader
import com.smooth.travelplanner.ui.Trip

@ExperimentalComposeUiApi
@Destination
@Composable
fun ArchivedTripsTab(

) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        item {
//            EmptySection()
//        }
        item {
            TabHeader(
                text = "Go back to your trips with memories!",
                modifier = Modifier
            )
        }
        items(2) {
            Trip(
                modifier = if (it == 2 - 1) Modifier.padding(bottom = 55.dp) else Modifier
            )
        }
    }
}