package com.smooth.travelplanner.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.util.Response
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface BaseMainRepository {
    fun refreshData(user: FirebaseUser?): Job

    fun getTripsWithSubCollections(idUser: String): Flow<Response<List<Trip>>>
}