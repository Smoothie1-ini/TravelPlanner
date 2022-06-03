package com.smooth.travelplanner.domain.model

import java.math.BigDecimal
import java.sql.Time
import java.time.Duration

data class TripEvent(
    val title: String,
    val description: String,
    val time: Time,
    val duration: Duration,
    val location: Pair<Float, Float>,
    val costEvent: BigDecimal,
    val rating: Int,
    val picture: String
)
