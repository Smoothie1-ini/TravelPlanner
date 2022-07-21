package com.smooth.travelplanner.domain.model

import java.util.*

data class TripDay(
    var id: String = "",
    val date: Date = Date(),
    var cost: Int = 0,
    var tripEvents: List<TripEvent> = listOf()
)
