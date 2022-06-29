package com.smooth.travelplanner.data.repository

import com.google.firebase.firestore.CollectionReference
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.TripDay
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
import com.smooth.travelplanner.util.Constants.TRIP_DAYS_REF
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreTripDaysRepositoryImpl @Inject constructor(
    private val tripsRef: CollectionReference,
) : BaseTripDaysRepository {
    override fun getTripDays(idTrip: String): Flow<Response<List<TripDay>>> = callbackFlow {
        val tripDaysRef = tripsRef.document(idTrip).collection(TRIP_DAYS_REF)
        val tripDays = mutableListOf<TripDay>()
        tripDaysRef
            .get()
            .addOnSuccessListener {
                for (doc in it.documents) {
                    val tripDay = doc.toObject(TripDay::class.java)
                    if (tripDay != null) {
                        tripDay.id = doc.id
                        tripDays.add(tripDay)
                    }
                }
                trySend(Response.Success(tripDays)).isSuccess
                close()
            }
            .addOnFailureListener {
                trySend(Response.Error(it.message ?: it.toString())).isFailure
                close()
            }
        awaitClose ()
    }

//    override fun getTripDays(idTrip: String): Flow<Response<List<TripDay>>> = callbackFlow {
//        tripsRef.document(idTrip).collection(TRIP_DAYS_REF)
//            .get()
//            .addOnSuccessListener {
//                val tripDays = mutableListOf<TripDay>()
//                for (doc in it.documents) {
//                    val tripDay = doc.toObject<TripDay>()
//                    if (tripDay != null) {
//                        tripDay.id = doc.id
//                        tripDays.add(tripDay)
//                    }
//                }
//                Response.Success(tripDays)
//            }
//            .addOnFailureListener {
//                Response.Error(it.message ?: it.toString())
//            }
//        awaitClose {
//
//        }
//    }

//    override fun getTripDays(idTrip: String): Flow<Response<List<TripDay>>> = callbackFlow {
//        val snapshotListener = tripsRef.document(idTrip).collection(TRIP_DAYS_REF)
//            .addSnapshotListener { snapshot, e ->
//                Log.d("", "")
//                val response = if (snapshot != null) {
//                    val tripDays = mutableListOf<TripDay>()
//                    for (doc in snapshot.documents) {
//                        val tripDay = doc.toObject(TripDay::class.java)
//                        if (tripDay != null) {
//                            tripDay.id = doc.id
//                            tripDays.add(tripDay)
//                        }
//                    }
//                    Response.Success(tripDays)
//                } else {
//                    Response.Error(e?.message ?: e.toString())
//                }
//                trySend(response).isSuccess
//            }
//        awaitClose {
//            snapshotListener.remove()
//        }
//    }

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