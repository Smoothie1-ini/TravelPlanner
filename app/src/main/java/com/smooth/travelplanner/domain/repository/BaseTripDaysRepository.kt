package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripDay
import kotlinx.coroutines.flow.Flow

interface BaseTripDaysRepository {
    fun getTripDays(idTrip: String): Flow<Response<List<TripDay>>>

    fun addTripDay(tripDay: TripDay): Flow<Response<Boolean>>

    fun updateTripDay(id: String, tripDay: TripDay): Flow<Response<Boolean>>

    fun deleteTripDay(id: String): Flow<Response<Boolean>>
}