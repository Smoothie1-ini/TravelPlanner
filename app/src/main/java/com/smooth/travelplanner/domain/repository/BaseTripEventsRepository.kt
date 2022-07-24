package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.TripEvent
import com.smooth.travelplanner.util.Response
import kotlinx.coroutines.flow.Flow

interface BaseTripEventsRepository {
    fun getTripEvents(idTrip: String, idTripDay: String): Flow<Response<List<TripEvent>>>

    fun addTripEvent(
        tripId: String,
        tripDayId: String,
        tripEventMap: Map<String, Any>
    ): Flow<Response<Boolean>>

    fun updateTripEvent(
        tripId: String,
        tripDayId: String,
        tripEventId: String,
        tripEventChanges: Map<String, Any>
    ): Flow<Response<Boolean>>

    fun deleteTripEvent(
        tripId: String,
        tripDayId: String,
        tripEventId: String
    ): Flow<Response<Boolean>>
}