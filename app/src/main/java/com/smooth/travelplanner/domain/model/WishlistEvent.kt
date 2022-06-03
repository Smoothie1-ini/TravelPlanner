package com.smooth.travelplanner.domain.model

import java.math.BigDecimal

data class WishlistEvent(
    val title: String,
    val description: String,
    val location: Pair<Float, Float>,
    val costEvent: BigDecimal
)
