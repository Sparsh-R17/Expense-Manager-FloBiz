package com.flobiz.expense_manager.repository

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun signInWithGoogle(account: GoogleSignInAccount): FirebaseUser? {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            authResult.user
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error logging in with Google: ${e.message}")
            null
        }
    }
}
