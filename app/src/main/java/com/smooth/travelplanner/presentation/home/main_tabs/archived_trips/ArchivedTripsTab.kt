package com.smooth.travelplanner.presentation.home.main_tabs.archived_trips

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.presentation.common.TabHeader

@ExperimentalComposeUiApi
@Destination
@Composable
fun ArchivedTripsTab(

) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TabHeader(
                text = "Go back to your trips with memories!",
                modifier = Modifier
            )
        }
        items(2) {

        }
    }
}