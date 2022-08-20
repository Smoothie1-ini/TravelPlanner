package com.smooth.travelplanner.domain.repository

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface BaseAuthRepository {
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?

    fun signOut(): FirebaseUser?

    fun getUser(): FirebaseUser?

    suspend fun sendPasswordReset(email: String): Boolean

    suspend fun oneTapSignInWithGoogle(): BeginSignInResult

    suspend fun oneTapSignUpWithGoogle(): BeginSignInResult

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): FirebaseUser?

    suspend fun revokeAccess(): FirebaseUser?
}