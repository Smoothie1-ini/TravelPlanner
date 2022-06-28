package com.smooth.travelplanner.domain.model

import java.util.*

data class TripDay(
    var id: String = "",
    val date: Date? = null,
    val costDay: String = "",
    val tripEvents: List<TripEvent> = listOf()
)
