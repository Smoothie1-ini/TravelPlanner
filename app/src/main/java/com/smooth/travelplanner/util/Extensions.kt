package com.smooth.travelplanner.util

import com.smooth.travelplanner.domain.model.Trip
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Trip.toMap(): Map<String, Any> {
    return mapOf<String, Any>(
        "idUser" to this.idUser,
        "title" to this.title,
        "description" to this.description,
        "cost" to this.cost
    )
}

fun Trip.getFirstDay(): Date? {
    if (this.tripDays.isNullOrEmpty()) return null
    var firstTripDay = Date(Long.MAX_VALUE)
    this.tripDays?.forEach {
        if (it.date == null)
            return@forEach
        if (it.date < firstTripDay)
            firstTripDay = it.date
    }
    return firstTripDay
}

fun Trip.getLastDay(): Date? {
    if (this.tripDays.isNullOrEmpty()) return null
    var lastDay = Date(Long.MIN_VALUE)
    this.tripDays?.forEach {
        if (it.date == null)
            return@forEach
        if (it.date > lastDay)
            lastDay = it.date
    }
    return lastDay
}

fun Date.toShortDateString(): String {
    val dateTime = LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
    return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
}