package com.smooth.travelplanner.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
import com.smooth.travelplanner.util.Constants.ID_USER
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreTripsRepositoryImpl @Inject constructor(
    private val tripsRef: CollectionReference,
    private val tripDaysRepository: BaseTripDaysRepository
) : BaseTripsRepository {
    override fun getTrips(idUser: String): Flow<Response<List<Trip>>> = callbackFlow {
        val snapshotListener = tripsRef
            .whereEqualTo(ID_USER, idUser)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val trips = mutableListOf<Trip>()
                    for (doc in snapshot.documents) {
                        val trip = doc.toObject(Trip::class.java)
                        if (trip != null) {
                            trip.id = doc.id
                            launch {
                                tripDaysRepository.getTripDays(doc.id).collect {
                                    when (it) {
                                        is Response.Loading -> {
                                            Log.d("FirestoreTripsRepositoryImpl", "Loading")
                                        }
                                        is Response.Success -> {
                                            trip.tripDays = it.data
                                        }
                                        is Response.Message -> {
                                            Log.d("FirestoreTripsRepositoryImpl", it.message)
                                        }
                                        is Response.Error -> {
                                            Response.Error(e?.message ?: e.toString())
                                        }
                                    }
                                }
                            }
                            trips.add(trip)
                        }
                    }
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

    override fun addTrip(trip: Trip): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.add(trip)
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun updateTrip(id: String, trip: Trip): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(id).set(trip, SetOptions.merge()).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun deleteTrip(id: String): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(id).delete().await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}