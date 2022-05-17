package com.smooth.travelplanner.ui.home.main_tabs.current_trips

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.ui.home.EmptySection
import com.smooth.travelplanner.ui.home.main_tabs.TabHeader
import com.smooth.travelplanner.ui.home.main_tabs.TripItem

@Destination
@Composable
fun CurrentTripsTab(

) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            EmptySection()
        }
        item {
            TabHeader(
                text = "Ready for a new trippin adventure?",
                modifier = Modifier
            )
        }
        items(10) {
            TripItem(
                modifier = if (it == 10 - 1) Modifier.padding(bottom = 55.dp) else Modifier
            )
        }
    }
}