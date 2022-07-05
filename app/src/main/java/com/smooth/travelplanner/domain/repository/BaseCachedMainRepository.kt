package com.smooth.travelplanner.domain.repository

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import kotlinx.coroutines.flow.StateFlow

interface BaseCachedMainRepository {
    val tripsWithSubCollectionsState: StateFlow<Response<List<Trip>>>

    fun cacheTripsWithSubCollections(trips: Response<List<Trip>>)

    fun getCurrentTripOrNull(tripId: String): Trip?
}