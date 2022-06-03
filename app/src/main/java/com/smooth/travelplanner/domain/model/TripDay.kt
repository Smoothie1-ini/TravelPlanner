package com.smooth.travelplanner.domain.model

import java.math.BigDecimal
import java.util.*

data class TripDay(
    val date: Date,
    val costDay: BigDecimal,
    val tripEvents: List<TripEvent>
)
