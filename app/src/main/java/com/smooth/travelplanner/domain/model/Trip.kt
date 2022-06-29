package com.smooth.travelplanner.domain.model

data class Trip(
    var id: String = "",
    var idUser: String = "",
    val title: String = "",
    val description: String = "",
    val cost: String = "",
    var tripDays: List<TripDay>? = null
)
