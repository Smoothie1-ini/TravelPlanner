package com.smooth.travelplanner.domain.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.util.*

data class TripEvent(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val time: Date? = null,
    val duration: Int = 0,
    val location: GeoPoint? = null,
    val cost: String = "",
    val rating: Int = 0,
    val picture: DocumentReference? = null
)
