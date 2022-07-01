package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripDay
import kotlinx.coroutines.flow.Flow

interface BaseTripDaysRepository {
    fun getTripDays(tripId: String): Flow<Response<List<TripDay>>>

    fun addTripDay(tripDay: TripDay): Flow<Response<Boolean>>

    fun updateTripDay(id: String, tripDay: TripDay): Flow<Response<Boolean>>

    fun deleteTripDay(tripId: String, tripDayId: String): Flow<Response<Boolean>>
}