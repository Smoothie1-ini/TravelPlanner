package com.smooth.travelplanner.data.repository

import com.google.firebase.firestore.CollectionReference
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreTripsRepositoryImpl @Inject constructor(
    private val tripsRef: CollectionReference
) : BaseTripsRepository {
    override fun getTrips(idUser: Int): Flow<Response<List<Trip>>> = callbackFlow {
        val snapshotListener = tripsRef
            .whereEqualTo("id_user", idUser)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val trips = snapshot.toObjects(Trip::class.java)
                    Response.Success(trips)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun addTrip(trip: Trip): Flow<Response<Void?>> {
        TODO("Not yet implemented")
    }

    override fun updateTrip(trip: Trip): Flow<Response<Void?>> {
        TODO("Not yet implemented")
    }

    override fun deleteTrip(trip: Trip): Flow<Response<Void?>> {
        TODO("Not yet implemented")
    }

}