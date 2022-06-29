package com.smooth.travelplanner.util

import com.smooth.travelplanner.domain.model.Trip

fun Trip.toMap():Map<String, Any> {
    return mapOf<String, Any>(
        "idUser" to this.idUser,
        "title" to this.title,
        "description" to this.description,
        "cost" to this.cost
    )
}