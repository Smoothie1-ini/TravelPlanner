package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import kotlinx.coroutines.flow.Flow

interface BaseTripsRepository {
    fun getTrips(idUser: String): Flow<Response<List<Trip>>>

    fun addTrip(trip: Trip): Flow<Response<Boolean>>

    fun updateTrip(id: String, trip: Trip): Flow<Response<Boolean>>

    fun deleteTrip(id: String): Flow<Response<Boolean>>
}