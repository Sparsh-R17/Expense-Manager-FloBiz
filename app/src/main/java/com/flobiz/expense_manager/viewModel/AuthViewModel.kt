package com.flobiz.expense_manager.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flobiz.expense_manager.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import  com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()


    private val _errorMessage = MutableLiveData<String?>()

//    val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val auth : FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun signWithEmail(email : String, pwd : String, home: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        home.invoke()
                        _authState.value = AuthState.Authenticated
                    } else {
                        Log.d("TAG", "signWithEmailPassword: ${task.result}")
                    }
                }
        }
        catch(ex : Exception) {
            Log.d("TAG", "signWithEmail: ${ex.message}")
        }
    }

    fun createWithEmail(
        name : String,
        email : String,
        pwd : String,
        home : () -> Unit
    ) {
        if(_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { task->
                    if(task.isSuccessful) {
                        auth.currentUser?.updateProfile(
                            UserProfileChangeRequest.Builder().setDisplayName(name).build()
                        )
                        home()
                    }
                    else {
                        Log.d("signtag", "createWithEmail: ${task.result}")
                    }
                }
            _loading.value = false
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        _loading.value = true
        viewModelScope.launch {
            val googleUser = authRepository.signInWithGoogle(account)
            _user.value = googleUser
            _loading.value = false
            _errorMessage.value =
                if (googleUser == null) "Google sign-in failed. Please try again." else null
        }
        _authState.value = AuthState.Authenticated
    }

    fun logout() {
        _loading.value = true
        auth.signOut()
        _loading.value = false
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val msg: String) : AuthState()
}