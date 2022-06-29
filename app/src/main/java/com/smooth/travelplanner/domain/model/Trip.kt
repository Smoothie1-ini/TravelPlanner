package com.smooth.travelplanner.domain.model

data class Trip(
    var id: String = "",
    var idUser: String = "",
    val title: String = "",
    val description: String = "",
    val costTrip: String = "",
    var tripDays: List<TripDay>? = null


)

