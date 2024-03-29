package com.smooth.travelplanner.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import com.smooth.travelplanner.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseTripsRepositoryImpl @Inject constructor(
    private val tripsRef: CollectionReference
) : BaseTripsRepository {
    override fun getTrips(idUser: String): Flow<Response<List<Trip>>> {
        TODO("Not yet implemented")
    }

    override fun addTrip(tripMap: Map<String, Any>): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.add(tripMap).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun updateTrip(tripId: String, tripChanges: Map<String, Any>): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(tripId).set(tripChanges, SetOptions.merge()).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun deleteTrip(tripId: String): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(tripId).delete().await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}