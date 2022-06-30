package com.smooth.travelplanner.data.cache

import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
class CachedMainRepositoryImpl : BaseCachedMainRepository {
    private val _tripsWithSubCollectionsState = MutableStateFlow<Response<List<Trip>>>(Response.Loading)
    override val tripsWithSubCollectionsState: StateFlow<Response<List<Trip>>>
        get() = _tripsWithSubCollectionsState

    override fun cacheTripsWithSubCollections(trips: Response<List<Trip>>) {
        this._tripsWithSubCollectionsState.value = trips
    }
}