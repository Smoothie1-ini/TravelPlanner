package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import kotlinx.coroutines.flow.Flow

interface BaseTripsRepository {
    fun getTrips(idUser: Int): Flow<Response<List<Trip>>>

    fun addTrip(trip: Trip): Flow<Response<Void?>>

    fun updateTrip(trip: Trip): Flow<Response<Void?>>

    fun deleteTrip(trip: Trip): Flow<Response<Void?>>
}