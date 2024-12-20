package com.flobiz.expense_manager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flobiz.expense_manager.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {
    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loginWithEmailAndPassword(email: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            val loggedInUser = authRepository.loginWithEmailAndPassword(email, password)
            _user.value = loggedInUser
            _loading.value = false
            _errorMessage.value = if (loggedInUser == null) "Login failed. Please try again." else null
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        _loading.value = true
        viewModelScope.launch {
            val googleUser = authRepository.signInWithGoogle(account)
            _user.value = googleUser
            _loading.value = false
            _errorMessage.value = if (googleUser == null) "Google sign-in failed. Please try again." else null
        }
    }

    fun logout() {
        authRepository.logout()
        _user.value = null
    }
}
