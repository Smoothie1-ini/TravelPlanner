package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.model.TripEvent
import com.smooth.travelplanner.util.Response
import kotlinx.coroutines.flow.StateFlow

interface BaseCachedMainRepository {
    val tripsWithSubCollectionsState: StateFlow<Response<List<Trip>>>

    fun cacheTripsWithSubCollections(trips: Response<List<Trip>>)

    fun getCurrentTripOrNull(tripId: String): Trip?

    fun getCurrentTripDayOrNull(tripDayId: String): TripDay?

    fun getCurrentTripEventOrNull(tripEventId: String): TripEvent?
}