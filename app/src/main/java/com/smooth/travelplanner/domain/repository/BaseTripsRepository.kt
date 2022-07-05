package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import kotlinx.coroutines.flow.Flow

interface BaseTripsRepository {
    fun getTrips(idUser: String): Flow<Response<List<Trip>>>

    fun addTrip(tripMap: Map<String, Any>): Flow<Response<Boolean>>

    fun updateTrip(tripId: String, tripChanges: Map<String, Any>): Flow<Response<Boolean>>

    fun deleteTrip(tripId: String): Flow<Response<Boolean>>
}