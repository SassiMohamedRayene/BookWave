package edu.gvsu.cis.bookwave.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    var currentUser: FirebaseUser? by mutableStateOf(auth.currentUser)
        private set

    var profileImageUrl by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        checkAuthStatus()
        loadProfileImage()
    }

    private fun checkAuthStatus() {
        currentUser = auth.currentUser
    }

    private fun loadProfileImage() {
        val uid = currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val document = firestore.collection("users")
                    .document(uid)
                    .get()
                    .await()

                profileImageUrl = document.getString("profileImageUrl")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user

                if (user != null) {
                    // Update display name
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()
                    user.updateProfile(profileUpdates).await()

                    // Create Firestore document
                    firestore.collection("users")
                        .document(user.uid)
                        .set(mapOf(
                            "username" to username,
                            "email" to email,
                            "profileImageUrl" to ""
                        ))
                        .await()

                    currentUser = auth.currentUser
                    authState = AuthState.Success("Account created successfully!")
                } else {
                    authState = AuthState.Error("Failed to create account")
                }
            } catch (e: Exception) {
                authState = AuthState.Error(e.message ?: "Sign up failed")
            }
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            authState = AuthState.Error("Please fill all fields")
            return
        }

        authState = AuthState.Loading

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                currentUser = auth.currentUser
                loadProfileImage()
                authState = AuthState.Success("Login successful!")
            } catch (e: Exception) {
                authState = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun uploadProfilePicture(imageUri: Uri, onComplete: (Boolean, String?) -> Unit) {
        val uid = currentUser?.uid ?: return

        isLoading = true

        viewModelScope.launch {
            try {
                // Upload to Storage
                val storageRef = storage.reference
                    .child("profile_pictures")
                    .child("$uid.jpg")

                storageRef.putFile(imageUri).await()

                // Get download URL
                val downloadUrl = storageRef.downloadUrl.await().toString()

                // Save to Firestore
                firestore.collection("users")
                    .document(uid)
                    .update("profileImageUrl", downloadUrl)
                    .await()

                // Update local state
                profileImageUrl = downloadUrl

                isLoading = false
                onComplete(true, "Profile picture updated!")

            } catch (e: Exception) {
                isLoading = false
                onComplete(false, e.message ?: "Upload failed")
            }
        }
    }

    fun updateProfile(
        newUsername: String,
        currentPassword: String? = null,
        newPassword: String? = null,
        onComplete: (Boolean, String?) -> Unit
    ) {
        val user = currentUser ?: return

        isLoading = true

        viewModelScope.launch {
            try {
                // Update username
                if (newUsername.isNotBlank() && newUsername != user.displayName) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(newUsername)
                        .build()
                    user.updateProfile(profileUpdates).await()

                    // Update in Firestore
                    firestore.collection("users")
                        .document(user.uid)
                        .update("username", newUsername)
                        .await()
                }

                // Update password if provided
                if (currentPassword != null && newPassword != null && newPassword.length >= 6) {
                    val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
                    user.reauthenticate(credential).await()
                    user.updatePassword(newPassword).await()
                }

                currentUser = auth.currentUser
                isLoading = false
                onComplete(true, "Profile updated successfully!")

            } catch (e: Exception) {
                isLoading = false
                onComplete(false, e.message ?: "Update failed")
            }
        }
    }

    fun logout() {
        auth.signOut()
        currentUser = null
        profileImageUrl = null
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