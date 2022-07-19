package com.smooth.travelplanner.domain.model

data class Trip(
    var id: String = "",
    var userId: String = "",
    val title: String = "",
    val description: String = "",
    val cost: String = "",
    var tripDays: List<TripDay> = listOf(),
    var isArchived: Boolean = false
)
