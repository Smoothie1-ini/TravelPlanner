package com.smooth.travelplanner.ui.home.main_tabs.wishlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.ui.home.main_tabs.TabHeader

@Destination
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
    }
}