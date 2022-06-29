package com.smooth.travelplanner.domain.model

import java.sql.Time

data class TripEvent(
    val title: String = "",
    val description: String = "",
    val time: Time? = null,
    val duration: Int = 0,
    val location: Pair<Float, Float> = Pair(0f, 0f),
    val costEvent: String = "",
    val rating: Int = 0,
    val picture: String = ""
)
