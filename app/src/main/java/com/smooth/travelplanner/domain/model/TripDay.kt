package com.smooth.travelplanner.domain.model

import java.util.*

data class TripDay(
    var id: String = "",
    val date: Date = Date(),
    val cost: String = "",
    var tripEvents: List<TripEvent> = listOf()
)
