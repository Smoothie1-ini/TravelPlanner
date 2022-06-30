package com.smooth.travelplanner.data.repository

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
class FirebaseTripsRepositoryImpl @Inject constructor(
    private val tripsRef: CollectionReference,
    private val tripDaysRepository: BaseTripDaysRepository
) : BaseTripsRepository {
    override fun getTrips(idUser: String): Flow<Response<List<Trip>>> = callbackFlow {
        val trips = mutableListOf<Trip>()
        tripsRef
            .whereEqualTo(ID_USER, idUser)
            .get()
            .addOnSuccessListener { snapshot ->
                launch {
                    for (doc in snapshot.documents) {
                        if (doc.id == "kJJFEatove5mxw4P70uq")
                            continue
                        val trip = doc.toObject(Trip::class.java)
                        if (trip != null) {
                            trip.id = doc.id
                            val tripDaysFlow = tripDaysRepository.getTripDays(trip.id)
                            tripDaysFlow.collect {
                                when (it) {
                                    is Response.Loading -> trySend(Response.Loading)
                                    is Response.Success -> trip.tripDays = it.data
                                    is Response.Error -> trySend(Response.Error(it.message)).isFailure
                                    is Response.Message -> trySend(Response.Message(it.message))
                                }
                            }
                            trips.add(trip)
                        }
                    }
                    trySend(Response.Success(trips)).isSuccess
                    close()
                }
            }
            .addOnFailureListener {
                trySend(Response.Error(it.message ?: it.toString())).isFailure
                close()
            }
        awaitClose()
    }

    override fun addTrip(tripMap: Map<String, Any>): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(tripsRef.document().id).set(tripMap).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun updateTrip(id: String, tripMap: Map<String, Any>): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(id).set(tripMap, SetOptions.merge()).await()
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