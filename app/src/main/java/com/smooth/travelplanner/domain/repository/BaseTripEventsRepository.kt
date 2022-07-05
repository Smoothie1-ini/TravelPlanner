package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripEvent
import kotlinx.coroutines.flow.Flow

interface BaseTripEventsRepository {
    fun getTripEvents(idTrip: String, idTripDay: String): Flow<Response<List<TripEvent>>>

    fun addTripEvent(tripEvent: TripEvent): Flow<Response<Boolean>>

    fun updateTripEvent(id: String, tripEvent: TripEvent): Flow<Response<Boolean>>

    fun deleteTripEvent(tripId: String, tripDayId: String, tripEventId: String): Flow<Response<Boolean>>
}