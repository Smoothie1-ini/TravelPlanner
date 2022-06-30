package com.smooth.travelplanner.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.smooth.travelplanner.data.cache.CachedMainRepositoryImpl
import com.smooth.travelplanner.data.repository.*
import com.smooth.travelplanner.domain.repository.*
import com.smooth.travelplanner.util.Constants.TRIPS_REF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): BaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth)
    }

    @Singleton
    @Provides
    fun provideCurrentUser(
        auth: FirebaseAuth
    ): FirebaseUser? = auth.currentUser

    @Singleton
    @Provides
    fun provideTripsRef(
        database: FirebaseFirestore
    ) = database.collection(TRIPS_REF)

    @Singleton
    @Provides
    fun provideCachedTripsRepositoryImpl(): BaseCachedMainRepository = CachedMainRepositoryImpl()

    @Singleton
    @Provides
    fun provideTripEventsRepository(
        tripsRef: CollectionReference
    ): BaseTripEventsRepository = FirebaseTripEventsRepositoryImpl(tripsRef)

    @Singleton
    @Provides
    fun provideTripDaysRepository(
        tripsRef: CollectionReference,
        tripEventsRepository: BaseTripEventsRepository
    ): BaseTripDaysRepository = FirebaseTripDaysRepository(tripsRef, tripEventsRepository)

    @Singleton
    @Provides
    fun provideTripsRepository(
        tripsRef: CollectionReference
    ): BaseTripsRepository = FirebaseTripsRepositoryImpl(tripsRef)

    @Singleton
    @Provides
    fun provideMainRepository(
        tripsRef: CollectionReference,
        tripDaysRepository: BaseTripDaysRepository,
        cachedMainRepository: BaseCachedMainRepository
    ): BaseMainRepository = FirebaseMainRepositoryImpl(tripsRef, tripDaysRepository, cachedMainRepository)
}