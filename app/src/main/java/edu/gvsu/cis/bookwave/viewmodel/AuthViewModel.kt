package edu.gvsu.cis.bookwave.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    var currentUser: FirebaseUser? by mutableStateOf(auth.currentUser)
        private set

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        currentUser = auth.currentUser
    }

    fun signUp(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            authState = AuthState.Error("Please fill all fields")
            return
        }

        if (password.length < 6) {
            authState = AuthState.Error("Password must be at least 6 characters")
            return
        }

        authState = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Update user profile with username
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                currentUser = auth.currentUser
                                authState = AuthState.Success("Account created successfully!")
                            } else {
                                authState = AuthState.Error(updateTask.exception?.message ?: "Failed to update profile")
                            }
                        }
                } else {
                    authState = AuthState.Error(task.exception?.message ?: "Sign up failed")
                }
            }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            authState = AuthState.Error("Please fill all fields")
            return
        }

        authState = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    currentUser = auth.currentUser
                    authState = AuthState.Success("Login successful!")
                } else {
                    authState = AuthState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun logout() {
        auth.signOut()
        currentUser = null
        authState = AuthState.Idle
    }

    fun resetAuthState() {
        authState = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}