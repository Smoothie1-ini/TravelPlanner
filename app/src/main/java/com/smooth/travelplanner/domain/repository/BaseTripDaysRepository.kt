package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripDay
import kotlinx.coroutines.flow.Flow

interface BaseTripDaysRepository {
    fun getTripDays(tripId: String): Flow<Response<List<TripDay>>>

    fun addTripDay(tripId: String, tripDayMap: Map<String, Any>): Flow<Response<Boolean>>

    fun updateTripDay(tripId: String, tripDayId: String, tripDayChanges: Map<String, Any>): Flow<Response<Boolean>>

    fun deleteTripDay(tripId: String, tripDayId: String): Flow<Response<Boolean>>
}