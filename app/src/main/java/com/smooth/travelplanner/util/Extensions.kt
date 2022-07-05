package com.smooth.travelplanner.util

import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.model.TripDay
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Trip.toMap(): Map<String, Any> {
    return mapOf<String, Any>(
        "idUser" to this.userId,
        "title" to this.title,
        "description" to this.description,
        "cost" to this.cost
    )
}

fun TripDay.toMap(): Map<String, Any> {
    return mapOf<String, Any>(
        "date" to this.date
    )
}

fun Trip.getFirstDay(): Date? {
    if (this.tripDays.isEmpty()) return null
    var firstTripDay = Date(Long.MAX_VALUE)
    this.tripDays.forEach {
        if (it.date < firstTripDay)
            firstTripDay = it.date
    }
    return firstTripDay
}

fun Trip.getLastDay(): Date? {
    if (this.tripDays.isEmpty()) return null
    var lastDay = Date(Long.MIN_VALUE)
    this.tripDays.forEach {
        if (it.date > lastDay)
            lastDay = it.date
    }
    return lastDay
}

fun Int.toHoursAndMinutes(): Pair<Int, Int> {
    val hours = this / 60
    val minutes = this - 60 * hours
    return Pair(hours, minutes)
}

fun Date.toShortDateString(): String {
    val dateTime = LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
    return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yy"))
}

fun Date.toLongDateString(): String {
    val dateTime = LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
    return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}

fun Date.toShortTimeString(): String {
    val dateTime = LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
    return dateTime.format(DateTimeFormatter.ofPattern("kk:mm"))
}

fun Date.toDayOfTheWeek(): String {
    val dateTime = LocalDateTime.ofInstant(this.toInstant(), ZoneId.systemDefault())
    return dateTime.format(DateTimeFormatter.ofPattern("EEEE"))
}