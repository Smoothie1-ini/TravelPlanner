package com.smooth.travelplanner.ui.home.main_tabs.wishlist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smooth.travelplanner.ui.home.main_tabs.TabHeader
import com.smooth.travelplanner.ui.home.main_tabs.TripItem

@Composable
fun WishlistTab() {
    LazyColumn {
        item {
            TabHeader(
                text = "Explore your dream places!",
                modifier = Modifier,
                color = MaterialTheme.colors.background
            )
        }
        items(5) {
            TripItem(
                modifier = if (it == 5 - 1) Modifier.padding(bottom = 55.dp) else Modifier
            )
        }
    }
}