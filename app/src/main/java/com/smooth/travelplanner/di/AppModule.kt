package com.smooth.travelplanner.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
import com.smooth.travelplanner.util.Constants.FIREBASE_GOOGLE_AUTH_CLIENT_ID
import com.smooth.travelplanner.util.Constants.SIGN_IN_REQ
import com.smooth.travelplanner.util.Constants.SIGN_UP_REQ
import com.smooth.travelplanner.util.Constants.TRIPS_REF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideContext(
        app: Application
    ): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideOneTapClient(
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named(SIGN_IN_REQ)
    fun provideSignInRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(FIREBASE_GOOGLE_AUTH_CLIENT_ID)
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named(SIGN_UP_REQ)
    fun provideSignUpRequest() = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(FIREBASE_GOOGLE_AUTH_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    fun provideGoogleSignInOptions() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(FIREBASE_GOOGLE_AUTH_CLIENT_ID)
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(SIGN_IN_REQ)
        signInRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQ)
        signUpRequest: BeginSignInRequest,
        signInClient: GoogleSignInClient
    ): BaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth, oneTapClient, signInRequest, signUpRequest, signInClient)
    }

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
    ): BaseMainRepository =
        FirebaseMainRepositoryImpl(tripsRef, tripDaysRepository, cachedMainRepository)

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE)

}