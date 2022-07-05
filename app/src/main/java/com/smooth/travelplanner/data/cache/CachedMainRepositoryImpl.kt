package com.smooth.travelplanner.data.cache

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.model.TripEvent
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
class CachedMainRepositoryImpl : BaseCachedMainRepository {
    //TODO Storing this as Response is not needed
    private val _tripsWithSubCollectionsState =
        MutableStateFlow<Response<List<Trip>>>(Response.Loading)
    override val tripsWithSubCollectionsState: StateFlow<Response<List<Trip>>>
        get() = _tripsWithSubCollectionsState

    override fun cacheTripsWithSubCollections(trips: Response<List<Trip>>) {
        this._tripsWithSubCollectionsState.value = trips
    }

    override fun getCurrentTripOrNull(tripId: String): Trip? {
        for (trip in (_tripsWithSubCollectionsState.value as Response.Success).data) {
            if (trip.id == tripId)
                return trip
        }
        return null
    }

    override fun getCurrentTripDayOrNull(tripDayId: String): TripDay? {
        for (trip in (_tripsWithSubCollectionsState.value as Response.Success).data) {
            for (tripDay in trip.tripDays) {
                if (tripDay.id == tripDayId)
                    return tripDay
            }
        }
        return null
    }

    override fun getCurrentTripEventOrNull(tripEventId: String): TripEvent? {
        for (trip in (_tripsWithSubCollectionsState.value as Response.Success).data) {
            for (tripDay in trip.tripDays) {
                for (tripEvent in tripDay.tripEvents) {
                    if (tripEvent.id == tripEventId) {
                        return tripEvent
                    }
                }
            }
        }
        return null
    }
}