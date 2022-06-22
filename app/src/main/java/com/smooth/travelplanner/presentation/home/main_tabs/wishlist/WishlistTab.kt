package com.smooth.travelplanner.presentation.home.main_tabs.wishlist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.smooth.travelplanner.presentation.common.TabHeader

@Destination
@Composable
fun WishlistTab() {
    LazyColumn {
        item {
            TabHeader(
                text = "Explore your dream places!"
            )
        }
    }
}