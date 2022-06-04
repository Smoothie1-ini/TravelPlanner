package com.smooth.travelplanner.domain.model

import java.math.BigDecimal

data class Trip(
    val title: String,
    val description: String,
    val isActive: Boolean,
    val costTrip: BigDecimal,
    val tripDays: List<TripDay>
)