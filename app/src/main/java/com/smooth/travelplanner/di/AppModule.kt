package com.smooth.travelplanner.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.smooth.travelplanner.data.repository.FirebaseAuthRepositoryImpl
import com.smooth.travelplanner.data.repository.FirestoreTripsRepositoryImpl
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import com.smooth.travelplanner.domain.repository.BaseTripsRepository
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
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): BaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideTripsRef(
        database: FirebaseFirestore
    ) = database.collection("trip")

    @Provides
    fun provideTripsRepository(
        tripsRef: CollectionReference
    ): BaseTripsRepository = FirestoreTripsRepositoryImpl(tripsRef)
}