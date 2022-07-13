package com.smooth.travelplanner.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
import com.smooth.travelplanner.domain.repository.BaseTripEventsRepository
import com.smooth.travelplanner.util.Constants.TRIP_DAYS_REF
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseTripDaysRepository @Inject constructor(
    private val tripsRef: CollectionReference,
    private val tripEventsRepository: BaseTripEventsRepository
) : BaseTripDaysRepository {
    override fun getTripDays(tripId: String): Flow<Response<List<TripDay>>> = callbackFlow {
        val tripDaysRef = tripsRef
            .document(tripId).collection(TRIP_DAYS_REF)
        val tripDays = mutableListOf<TripDay>()
        tripDaysRef
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                launch {
                    for (doc in snapshot.documents) {
                        val tripDay = doc.toObject(TripDay::class.java)
                        if (tripDay != null) {
                            tripDay.id = doc.id
                            val tripDaysFlow =
                                tripEventsRepository.getTripEvents(tripId, tripDay.id)
                            tripDaysFlow.collect {
                                when (it) {
                                    is Response.Loading -> trySend(Response.Loading)
                                    is Response.Success -> {
                                        tripDay.tripEvents = it.data
                                        tripDays.add(tripDay)
                                    }
                                    is Response.Error -> {
                                        trySend(Response.Error(it.message)).isFailure
                                        close()
                                    }
                                    is Response.Message -> trySend(Response.Message(it.message))
                                }
                            }
                        }
                    }
                    trySend(Response.Success(tripDays)).isSuccess
                    close()
                }
            }
            .addOnFailureListener {
                trySend(Response.Error(it.message ?: it.toString())).isFailure
                close()
            }
        awaitClose()
    }

    override fun addTripDay(
        tripId: String,
        tripDayMap: Map<String, Any>
    ): Flow<Response<Boolean>> =
        flow {
            try {
                emit(Response.Loading)
                val tripDaysRef = tripsRef.document(tripId).collection(TRIP_DAYS_REF)
                tripDaysRef.add(tripDayMap).await()
                emit(Response.Success(true))
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: e.toString()))
            }
        }

    override fun updateTripDay(
        tripId: String,
        tripDayId: String,
        tripDayChanges: Map<String, Any>
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(tripId).collection(TRIP_DAYS_REF)
                .document(tripDayId).set(tripDayChanges, SetOptions.merge()).await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override fun deleteTripDay(
        tripId: String,
        tripDayId: String
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            tripsRef.document(tripId).collection(TRIP_DAYS_REF)
                .document(tripDayId).delete().await()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}