package com.smooth.travelplanner.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : BaseAuthRepository {
    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        auth.createUserWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }

    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        auth.signInWithEmailAndPassword(email, password).await()
        return auth.currentUser
    }

    override fun signOut(): FirebaseUser? {
        auth.signOut()
        return auth.currentUser
    }

    override fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun sendPasswordReset(email: String): Boolean {
        auth.sendPasswordResetEmail(email).await()
        return true
    }
}