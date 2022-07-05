package com.smooth.travelplanner.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripEvent
import com.smooth.travelplanner.domain.repository.BaseTripEventsRepository
import com.smooth.travelplanner.util.Constants.TRIP_DAYS_REF
import com.smooth.travelplanner.util.Constants.TRIP_EVENTS_REF
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseTripEventsRepositoryImpl @Inject constructor(
    private val tripsRef: CollectionReference
) : BaseTripEventsRepository {
    override fun getTripEvents(idTrip: String, idTripDay: String): Flow<Response<List<TripEvent>>> =
        callbackFlow {
            val tripEventsRef = tripsRef
                .document(idTrip).collection(TRIP_DAYS_REF)
                .document(idTripDay).collection(TRIP_EVENTS_REF)
            val tripEvents = mutableListOf<TripEvent>()
            tripEventsRef
                .get()
                .addOnSuccessListener { snapshot ->
                    for (doc in snapshot.documents) {
                        val tripEvent = doc.toObject(TripEvent::class.java)
                        if (tripEvent != null) {
                            tripEvent.id = doc.id
                            tripEvents.add(tripEvent)
                        }
                    }
                    trySend(Response.Success(tripEvents)).isSuccess
                    close()
                }
                .addOnFailureListener {
                    trySend(Response.Error(it.message ?: it.toString())).isFailure
                    close()
                }
            awaitClose()
        }

    override fun addTripEvent(
        tripId: String,
        tripDayId: String,
        tripEventMap: Map<String, Any>
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            val tripEventsRef = tripsRef.document(tripId).collection(TRIP_DAYS_REF)
                .document(tripDayId).collection(TRIP_EVENTS_REF)
            tripEventsRef.add(tripEventMap).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun updateTripEvent(
        tripId: String,
        tripDayId: String,
        tripEventId: String,
        tripEventChanges: Map<String, Any>
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(tripId).collection(TRIP_DAYS_REF)
                .document(tripDayId).collection(TRIP_EVENTS_REF)
                .document(tripEventId).set(tripEventChanges, SetOptions.merge()).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun deleteTripEvent(
        tripId: String,
        tripDayId: String,
        tripEventId: String
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(tripId).collection(TRIP_DAYS_REF)
                .document(tripDayId).collection(TRIP_EVENTS_REF)
                .document(tripEventId).delete().await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

}