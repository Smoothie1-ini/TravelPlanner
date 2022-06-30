package com.smooth.travelplanner.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.smooth.travelplanner.domain.model.Response
import com.smooth.travelplanner.domain.model.Trip
import com.smooth.travelplanner.domain.repository.BaseCachedMainRepository
import com.smooth.travelplanner.domain.repository.BaseMainRepository
import com.smooth.travelplanner.domain.repository.BaseTripDaysRepository
import com.smooth.travelplanner.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirebaseMainRepositoryImpl @Inject constructor(
    private val tripsRef: CollectionReference,
    private val tripDaysRepository: BaseTripDaysRepository,
    private val cachedMainRepository: BaseCachedMainRepository
) : BaseMainRepository {
    override fun refreshData(user: FirebaseUser?) = CoroutineScope(Dispatchers.IO).launch {
        if (user != null)
            getTripsWithSubCollections(user.uid).collect {
                cachedMainRepository.cacheTripsWithSubCollections(it)
            }
    }

    override fun getTripsWithSubCollections(idUser: String): Flow<Response<List<Trip>>> =
        callbackFlow {
            val trips = mutableListOf<Trip>()
            tripsRef
                .whereEqualTo(Constants.ID_USER, idUser)
                .get()
                .addOnSuccessListener { snapshot ->
                    launch {
                        for (doc in snapshot.documents) {
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
}