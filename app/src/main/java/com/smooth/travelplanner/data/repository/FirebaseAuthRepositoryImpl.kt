package com.smooth.travelplanner.data.repository

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.smooth.travelplanner.domain.repository.BaseAuthRepository
import com.smooth.travelplanner.util.Constants.SIGN_IN_REQ
import com.smooth.travelplanner.util.Constants.SIGN_UP_REQ
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQ)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQ)
    private var signUpRequest: BeginSignInRequest,
    private var signInClient: GoogleSignInClient,
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

    override suspend fun oneTapSignInWithGoogle(): BeginSignInResult {
        return oneTapClient.beginSignIn(signInRequest).await()
    }

    override suspend fun oneTapSignUpWithGoogle(): BeginSignInResult {
        return oneTapClient.beginSignIn(signUpRequest).await()
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): FirebaseUser? {
        auth.signInWithCredential(googleCredential).await()
        return auth.currentUser
    }

    override suspend fun revokeAccess(): FirebaseUser? {
        auth.currentUser?.apply {
            signInClient.revokeAccess().await()
            oneTapClient.signOut().await()
            delete().await()
        }
        return auth.currentUser
    }
}