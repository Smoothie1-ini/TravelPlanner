package com.smooth.travelplanner.data.repository

import com.google.firebase.firestore.CollectionReference
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
import com.smooth.travelplanner.domain.repository.BaseTripEventsRepository
import com.smooth.travelplanner.util.Constants.TRIP_DAYS_REF
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseTripDaysRepository @Inject constructor(
    private val tripsRef: CollectionReference,
    private val tripEventsRepository: BaseTripEventsRepository
) : BaseTripDaysRepository {
    override fun getTripDays(idTrip: String): Flow<Response<List<TripDay>>> = callbackFlow {
        val tripDaysRef = tripsRef
            .document(idTrip).collection(TRIP_DAYS_REF)
        val tripDays = mutableListOf<TripDay>()
        tripDaysRef
            .get()
            .addOnSuccessListener { snapshot ->
                launch {
                    for (doc in snapshot.documents) {
                        val tripDay = doc.toObject(TripDay::class.java)
                        if (tripDay != null) {
                            tripDay.id = doc.id
                            val tripDaysFlow = tripEventsRepository.getTripEvents(idTrip, tripDay.id)
                            tripDaysFlow.collect {
                                when (it) {
                                    is Response.Loading -> trySend(Response.Loading)
                                    is Response.Success -> tripDay.tripEvents = it.data
                                    is Response.Error -> trySend(Response.Error(it.message)).isFailure
                                    is Response.Message -> trySend(Response.Message(it.message))
                                }
                            }
                            tripDays.add(tripDay)
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
        awaitClose ()
    }

    override fun addTripDay(tripDay: TripDay): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun updateTripDay(id: String, tripDay: TripDay): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun deleteTripDay(id: String): Flow<Response<Boolean>> {
        TODO("Not yet implemented")
    }
}