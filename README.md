# BookWave Tutorial

## Building an Intelligent Audiobook Application with Android, Kotlin, and Firebase

---

## Table of Contents

1. [Overview](#overview)
2. [Getting Started](#getting-started)
3. [Step-by-Step Coding Instructions](#step-by-step-coding-instructions)
4. [Conclusions](#conclusions)
5. [See Also](#see-also)

---

## Overview

In this tutorial, we will explore how to build **BookWave**, an intelligent audiobook application created with Android Studio, Kotlin, and Jetpack Compose. The focus area of this tutorial is how to use Firebase as a backend together with modern Android development tools such as Compose, Navigation, ViewModel, and Coroutines.

BookWave is designed for readers and audiobook lovers. It combines a clean and modern UI with powerful cloud services. As readers follow this tutorial, they will build a simple demo version of BookWave that includes:

- **Jetpack Compose** → declarative UI, state management, navigation
- **Firebase Authentication** → secure login & sign-up
- **Cloud Firestore** → storing user profiles and messages
- **Firebase Storage** → uploading and retrieving profile photos
- **Firebase Cloud Messaging (FCM)** → push notifications
- **Media3 ExoPlayer** → building an audiobook player
- **Gemini AI (Google Generative AI)** → creating an AI-powered book assistant

### About the Demo App

BookWave is an Android application designed for audiobook listeners and book lovers. Throughout this tutorial, you will build the core features of BookWave, including:

### Key Features

#### Audiobook Player
A modern player interface inspired by Spotify:
- Play / Pause
- Seekbar
- Media3 ExoPlayer integration

#### AI-Powered Book Assistant
Using Gemini AI, the chatbot can:
- Summarize books
- Recommend books by genre
- Answer book-related questions
- Provide reading suggestions

#### Netflix-style Home Screen
The Home screen displays:
- Curated book collections
- New arrivals

All using Jetpack Compose LazyRow + LazyColumn layouts.

#### Book Details Screen
Displays book cover, title, author, rating, summary, and a button that opens the audiobook player.

#### Reader Community
Users can:
- Send & receive messages
- Chat with friends
- Share book recommendations

Built with Firebase Firestore (Realtime updates).

#### User Profile & Personalization
Users can:
- Upload a profile photo (Firebase Storage)
- Edit profile info

#### Push Notifications
Using Firebase Messaging, users receive notifications about:
- New book releases
- App updates

#### Authentication
Login and Sign-Up screens using Firebase Authentication.

### ⭐ What You Will Learn

In this tutorial, you will learn:
- How to connect an Android Jetpack Compose app to Firebase
- How to use Firebase Authentication
- How to store and read data with Firestore
- How to upload images to Firebase Storage
- How to play audiobooks using Media3 ExoPlayer
- How to organize a modern Compose app using Navigation and ViewModels
- How to integrate simple AI features using Gemini

---

## Getting Started

To follow this tutorial, you need a fully working Android development environment and a Firebase project. Below are the exact tools and dependencies required.

### Prerequisites

#### Software Requirements

| Tool | Version |
|------|---------|
| Android Studio | Narval 3 (2025.1.3) |
| JDK | OpenJDK 17+ |
| Android SDK | API 24+ |
| Firebase Console | Active project |
| Gradle | Automatically managed by Android Studio |

#### Required Knowledge

You should already be familiar with:
- Basic Kotlin syntax
- Android Studio usage
- Jetpack Compose basics

No prior knowledge of Firebase or Gemini AI is required — the tutorial will guide you.

### Project Dependencies

Paste the following into your `build.gradle.kts` (module level):

#### 🔥 Firebase Dependencies

These are required for authentication, database, storage, analytics, notifications:

```kotlin
implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")
implementation("com.google.firebase:firebase-storage-ktx")
implementation("com.google.firebase:firebase-analytics-ktx")
implementation("com.google.firebase:firebase-messaging:25.0.1")
```

#### 🎨 Jetpack Compose

```kotlin
implementation(platform("androidx.compose:compose-bom:2024.02.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-graphics")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")
debugImplementation("androidx.compose.ui:ui-tooling")
```

#### Navigation, ViewModel & Lifecycle

```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
```

#### Coroutines

```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
```

#### Coil (image loading)

```kotlin
implementation("io.coil-kt:coil-compose:2.5.0")
```

#### Networking (Retrofit)

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
```

#### Media3 (audiobook player)

```kotlin
implementation("androidx.media3:media3-exoplayer:1.2.1")
implementation("androidx.media3:media3-ui:1.2.1")
```

#### Gemini AI API

```kotlin
implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
```

### Setting Up Firebase

#### Step 1 — Create Your Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **Add project**
3. Choose a name (e.g., BookWave)
4. Click **Continue**

#### Step 2 — Add Android App

1. In Firebase → Project Overview → Add App
2. Select **Android**
3. Enter:
   - **Package Name**: must match the one in `AndroidManifest.xml`
   - Example: `com.bookwave.app`
4. Download the file: `google-services.json`
5. Place it inside: `app/src/main/`

#### Step 3 — Add Firebase Gradle Plugins

In project-level `build.gradle.kts`:

```kotlin
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")
    }
}
```

In module-level `build.gradle.kts`:

```kotlin
plugins {
    id("com.google.gms.google-services")
}
```

#### Step 4 — Enable Firebase Services

Inside the Firebase Console:

**✔ Authentication**
- Go to Authentication → Sign-in method
- Enable Email/Password
- Enable Google Sign-In (optional)

**✔ Firestore Database**
- Go to Firestore Database
- Click Create database
- Use Start in test mode for development

**✔ Firebase Storage**
- Go to Storage
- Click Create storage bucket

**✔ Cloud Messaging**
- Go to Cloud Messaging
- No configuration needed for now

### Verifying the Setup

Add this line in `MainActivity` to ensure Firebase initialized correctly:

```kotlin
FirebaseApp.initializeApp(this)
Log.d("Firebase", "Initialized!")
```

Run the app → check Logcat → should show `Initialized!`

---

## Step-by-Step Coding Instructions

This section provides a detailed walkthrough of the coding process, from setting up the project to implementing advanced features like AI chat and push notifications. Each step includes annotated code blocks and explanations to guide you.

### A. Authentication

#### 1. AuthViewModel.kt

This ViewModel handles all authentication logic, exposing the current UI state via a `StateFlow`. A sealed class is used to model these states robustly.

```kotlin
// app/src/main/java/com/bookwave/viewmodel/AuthViewModel.kt
package com.bookwave.viewmodel // TODO: Replace with your package name

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookwave.data.di.FirebaseModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth = FirebaseModule.auth
    private val _authState = MutableStateFlow<AuthState>(
        if (auth.currentUser != null) AuthState.Authenticated else AuthState.Unauthenticated
    )
    val authState: StateFlow<AuthState> = _authState

    init {
        auth.addAuthStateListener {
            _authState.value = if (it.currentUser != null) AuthState.Authenticated else AuthState.Unauthenticated
        }
    }

    fun signIn(email: String, pass: String) = viewModelScope.launch {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Email and password cannot be empty.")
            return@launch
        }
        _authState.value = AuthState.Loading
        try {
            auth.signInWithEmailAndPassword(email, pass).await()
            // The AuthStateListener will automatically update the state to Authenticated
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Login Failed")
        }
    }

    fun signUp(email: String, pass: String) = viewModelScope.launch {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Email and password cannot be empty.")
            return@launch
        }
        _authState.value = AuthState.Loading
        try {
            auth.createUserWithEmailAndPassword(email, pass).await()
            // AuthStateListener will handle the state change
        } catch (e: Exception) {
            _authState.value = AuthState.Error(e.message ?: "Sign Up Failed")
        }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}
```

The `AuthStateListener` in the init block ensures that the app's state automatically reacts to authentication changes, such as when a user's session expires. The `signIn` and `signUp` functions use `viewModelScope` to launch coroutines, ensuring that the operations are lifecycle-aware and automatically canceled if the ViewModel is cleared.

#### 2. LoginScreen.kt & SignUpScreen.kt Composables

These UI components observe the ViewModel's state and trigger actions. They are designed to be as stateless as possible, with logic centralized in the ViewModel.

```kotlin
// app/src/main/java/com/bookwave/ui/screen/LoginScreen.kt
package com.bookwave.ui.screen // TODO: Replace with your package name

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookwave.viewmodel.AuthViewModel
import com.bookwave.viewmodel.AuthState

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = email, 
            onValueChange = { email = it }, 
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(contentAlignment = Alignment.Center) {
            when (val state = authState) {
                is AuthState.Loading -> CircularProgressIndicator()
                is AuthState.Error -> Text(
                    state.message, 
                    color = MaterialTheme.colorScheme.error
                )
                else -> Button(
                    onClick = { authViewModel.signIn(email, password) },
                    enabled = authState !is AuthState.Loading
                ) {
                    Text("Login")
                }
            }
        }

        TextButton(onClick = onNavigateToSignUp) {
            Text("Don't have an account? Sign Up")
        }
    }
}
```

The `SignUpScreen.kt` follows a nearly identical pattern. `LaunchedEffect` is a key component here, allowing us to perform a side-effect (like navigation) in response to a state change (`authState`). The UI dynamically shows a progress indicator or an error message based on the `AuthState`.

---

### B. Firestore Data Model & Repository

We need data models to structure our information and a repository to abstract data access logic.

#### 1. Data Models

Define simple Kotlin data classes that map directly to your Firestore document structures.

```kotlin
// app/src/main/java/com/bookwave/data/model/UserProfile.kt
package com.bookwave.data.model

data class UserProfile(
    val uid: String = "",
    val email: String? = null,
    val displayName: String? = null,
    val profilePictureUrl: String? = null
)
```

```kotlin
// app/src/main/java/com/bookwave/data/model/Book.kt
package com.bookwave.data.model

data class Book(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val coverImageUrl: String = "",
    val audioUrl: String = ""
)
```

```kotlin
// app/src/main/java/com/bookwave/data/model/Message.kt
package com.bookwave.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Message(
    val senderId: String = "",
    val senderName: String = "",
    val text: String = "",
    @ServerTimestamp
    val timestamp: Timestamp? = null
)
```

The `@ServerTimestamp` annotation on the timestamp field in `Message` is a powerful Firestore feature. It tells Firestore to automatically populate this field with the server's current time when the document is created, ensuring consistent timestamps across all clients.

#### 2. FirestoreRepository.kt

This class centralizes all Firestore operations, providing a clean API for your ViewModels.

```kotlin
// app/src/main/java/com/bookwave/data/repository/FirestoreRepository.kt
package com.bookwave.data.repository

import com.bookwave.data.di.FirebaseModule
import com.bookwave.data.model.Message
import com.bookwave.data.model.UserProfile
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val firestore = FirebaseModule.firestore
    private val usersCollection = firestore.collection("users")
    private val chatsCollection = firestore.collection("chats")

    suspend fun createUserProfile(profile: UserProfile) {
        usersCollection.document(profile.uid).set(profile).await()
    }

    suspend fun getUserProfile(uid: String): UserProfile? {
        val doc = usersCollection.document(uid).get().await()
        return doc.toObject(UserProfile::class.java)
    }

    suspend fun updateProfile(uid: String, newProfileData: Map<String, Any>) {
        usersCollection.document(uid).update(newProfileData).await()
    }

    suspend fun sendMessage(chatId: String, message: Message) {
        chatsCollection.document(chatId).collection("messages").add(message).await()
    }

    fun getMessagesStream(chatId: String): Flow<List<Message>> {
        return chatsCollection.document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .snapshots()
            .map { snapshot -> snapshot.toObjects(Message::class.java) }
    }
}
```

This repository demonstrates two key interaction types with Firebase. For one-time operations like fetching a user profile, it uses suspend functions with `.await()`. For real-time updates, like in a chat, it returns a Kotlin `Flow` by using the `.snapshots()` extension function, which emits a new list of messages whenever the underlying data changes in Firestore.

---

### C. Profile Image Upload (Storage)

Allow users to upload a profile picture from their device's gallery to Firebase Storage.

#### 1. Image Picker and Upload Logic

We combine the UI and logic in a `ProfileViewModel` and use the `rememberLauncherForActivityResult` API in the Composable to pick an image from the gallery.

```kotlin
// app/src/main/java/com/bookwave/ui/screen/ProfileScreen.kt (Snippet)
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bookwave.data.di.FirebaseModule
import com.bookwave.data.repository.FirestoreRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// In a new file: viewmodel/ProfileViewModel.kt
class ProfileViewModel : ViewModel() {
    private val storage = FirebaseModule.storage
    private val firestoreRepo = FirestoreRepository()
    private val userId = FirebaseModule.auth.currentUser?.uid ?: ""

    // State for the UI to observe
    val profilePictureUrl = MutableStateFlow<String?>(null)

    fun uploadProfilePicture(uri: Uri) = viewModelScope.launch {
        val storageRef = storage.reference.child("profile_pictures/$userId.jpg")
        storageRef.putFile(uri).await()
        val downloadUrl = storageRef.downloadUrl.await().toString()
        firestoreRepo.updateProfile(userId, mapOf("profilePictureUrl" to downloadUrl))
        profilePictureUrl.value = downloadUrl // Update UI state
    }
}

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel()) {
    val profilePicUrl by profileViewModel.profilePictureUrl.collectAsState()
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { profileViewModel.uploadProfilePicture(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = profilePicUrl,
            contentDescription = "Profile Picture",
            modifier = Modifier.size(120.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Change Picture")
        }
    }
}
```

This flow is straightforward:

1. The `rememberLauncherForActivityResult` provides a safe way to launch the system's image picker and receive the result.
2. The returned `Uri` (a pointer to the local image file) is passed to the ViewModel.
3. The ViewModel uploads the file to a specific path in Firebase Storage (e.g., `profile_pictures/USER_ID.jpg`).
4. After the upload succeeds, it retrieves the public download URL and saves it to the user's profile in Firestore.

---

### D. Audiobook Player (Media3)

Implement a simple audio player using Google's modern Media3 library.

#### 1. PlayerManager.kt

Create a manager class to encapsulate the ExoPlayer instance and its state. For a production app that requires background playback and notification controls, this logic should be moved into a `MediaBrowserService`.

```kotlin
// app/src/main/java/com/bookwave/player/PlayerManager.kt
package com.bookwave.player // TODO: Replace with your package name

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerManager(context: Context) {
    private val player: ExoPlayer = ExoPlayer.Builder(context).build()
    private val _playerState = MutableStateFlow(false)
    val isPlaying = _playerState.asStateFlow()

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _playerState.value = isPlaying
            }
        })
    }

    fun play(audioUrl: String) {
        val mediaItem = MediaItem.fromUri(audioUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun release() {
        player.release()
    }
}
```

#### 2. PlayerScreen.kt Composable

This screen provides the UI controls for the player and cleans up resources when it's no longer visible.

```kotlin
// app/src/main/java/com/bookwave/ui/screen/PlayerScreen.kt
package com.bookwave.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bookwave.data.model.Book
import com.bookwave.player.PlayerManager

@Composable
fun PlayerScreen(book: Book) {
    val context = LocalContext.current
    val playerManager = remember { PlayerManager(context) }
    val isPlaying by playerManager.isPlaying.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            playerManager.release()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = book.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = book.author, style = MaterialTheme.typography.bodyLarge)
        
        Row {
            if (isPlaying) {
                IconButton(onClick = { playerManager.pause() }) {
                    Icon(Icons.Default.Pause, contentDescription = "Pause")
                }
            } else {
                IconButton(onClick = { playerManager.play(book.audioUrl) }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play")
                }
            }
        }
        
        // TODO: Add a Slider for seek control and to display progress
    }
}
```

The `DisposableEffect` is crucial for resource management. It ensures that the `playerManager.release()` function is called when the `PlayerScreen` leaves the composition, freeing up the ExoPlayer instance and preventing memory leaks.

---

### E. Chat / Community Messaging UI

Build a real-time chat screen using the Flow from our `FirestoreRepository`.

```kotlin
// app/src/main/java/com/bookwave/viewmodel/ChatViewModel.kt
package com.bookwave.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookwave.data.model.Message
import com.bookwave.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(private val chatId: String) : ViewModel() {
    private val repository = FirestoreRepository()

    val messages: StateFlow<List<Message>> = repository.getMessagesStream(chatId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun sendMessage(text: String, senderId: String, senderName: String) = viewModelScope.launch {
        if (text.isNotBlank()) {
            val message = Message(
                text = text,
                senderId = senderId,
                senderName = senderName
            )
            repository.sendMessage(chatId, message)
        }
    }
}
```

```kotlin
// app/src/main/java/com/bookwave/ui/screen/ChatScreen.kt
package com.bookwave.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookwave.data.model.Message
import com.bookwave.viewmodel.ChatViewModel

@Composable
fun ChatScreen(chatId: String, currentUserId: String, currentUserName: String) {
    // In a real app, you would pass the factory to the viewModel composable
    val chatViewModel: ChatViewModel = viewModel() // Simplified for tutorial
    val messages by chatViewModel.messages.collectAsState()
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f).padding(8.dp)) {
            items(messages) { message ->
                MessageBubble(message, message.senderId == currentUserId)
            }
        }

        Row(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                chatViewModel.sendMessage(text, currentUserId, currentUserName)
                text = "" // Clear input field
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message, isCurrentUser: Boolean) {
    // TODO: Add styling to align message left/right and use different colors
    Text(
        text = "${message.senderName}: ${message.text}",
        modifier = Modifier.padding(4.dp)
    )
}
```

The `stateIn` operator in the `ChatViewModel` is an efficient way to convert a cold `Flow` from the repository into a hot `StateFlow`. This means the ViewModel will listen for Firestore updates only when there is at least one UI collector (i.e., when the `ChatScreen` is visible), and it will stop listening after a timeout (5000ms) when the screen is gone, saving resources.

---

### F. Gemini AI Integration

Integrate Google's Gemini AI to provide book summaries or recommendations.

#### 1. AiRepository.kt & AiViewModel.kt

The repository encapsulates the Gemini API client, and the ViewModel manages the state for the AI chat UI.

```kotlin
// app/src/main/java/com/bookwave/data/repository/AiRepository.kt
package com.bookwave.data.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.google.ai.client.generativeai.type.PromptBlockedException
import com.google.ai.client.generativeai.type.ServerException

class AiRepository {
    // TODO: Add your Gemini API Key from Google AI Studio. Do not hardcode in production!
    private val apiKey = "YOUR_API_KEY_HERE"
    
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey,
        generationConfig = generationConfig { temperature = 0.7f }
    )

    suspend fun getBookSummary(prompt: String): Result<String> {
        return try {
            val response = generativeModel.generateContent(prompt)
            Result.success(response.text ?: "Sorry, I couldn't generate a response.")
        } catch (e: PromptBlockedException) {
            Result.failure(Exception("Your request was blocked for safety reasons."))
        } catch (e: ServerException) {
            Result.failure(Exception("The server is currently unavailable. Please try again later."))
        } catch (e: Exception) {
            Result.failure(Exception("An unknown error occurred: ${e.localizedMessage}"))
        }
    }
}
```

```kotlin
// app/src/main/java/com/bookwave/viewmodel/AiViewModel.kt
package com.bookwave.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookwave.data.repository.AiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AiState {
    object Idle : AiState()
    object Loading : AiState()
    data class Success(val responseText: String) : AiState()
    data class Error(val message: String) : AiState()
}

class AiViewModel : ViewModel() {
    private val repository = AiRepository()
    private val _responseState = MutableStateFlow<AiState>(AiState.Idle)
    val responseState: StateFlow<AiState> = _responseState

    fun generateSummary(prompt: String) = viewModelScope.launch {
        _responseState.value = AiState.Loading
        repository.getBookSummary(prompt)
            .onSuccess { response ->
                _responseState.value = AiState.Success(response)
            }
            .onFailure { error ->
                _responseState.value = AiState.Error(error.message ?: "An error occurred")
            }
    }
}
```

The `AiRepository` returns a `Result` wrapper, which is a great Kotlin pattern for explicitly handling success and failure cases. This makes the error handling in the `AiViewModel` very clean and safe, preventing app crashes from unexpected API responses or network issues.

#### 2. ChatbotScreen.kt

The UI is straightforward, observing the `AiState` from the ViewModel.

```kotlin
// app/src/main/java/com/bookwave/ui/screen/ChatbotScreen.kt
package com.bookwave.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bookwave.viewmodel.AiState
import com.bookwave.viewmodel.AiViewModel

@Composable
fun ChatbotScreen(aiViewModel: AiViewModel = viewModel()) {
    val aiResponseState by aiViewModel.responseState.collectAsState()
    var prompt by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = prompt,
            onValueChange = { prompt = it },
            label = { Text("Ask for a book summary...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        
        Button(onClick = { aiViewModel.generateSummary(prompt) }) {
            Text("Send")
        }
        
        Spacer(Modifier.height(16.dp))

        when(val state = aiResponseState) {
            is AiState.Loading -> CircularProgressIndicator()
            is AiState.Success -> Text(state.responseText)
            is AiState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            is AiState.Idle -> Text("Ask me anything about books!")
        }
    }
}
```

---

### G. Push Notifications (FCM)

Set up Firebase Cloud Messaging to receive and handle push notifications.

#### 1. MyFirebaseMessagingService.kt

This service runs in the background to handle incoming messages.

```kotlin
// app/src/main/java/com/bookwave/fcm/MyFirebaseMessagingService.kt
package com.bookwave.fcm // TODO: Replace with your package name

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FCM_Service"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Handle data messages
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Handle notification messages
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            // Here, you would build and display a system notification
            // using NotificationManagerCompat
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        // This token identifies the device. In a real application, you would
        // send this token to your backend server to target this device for notifications.
    }
}
```

#### AndroidManifest.xml

Declare the service and specify a default notification icon.

```xml
<!-- app/src/main/AndroidManifest.xml -->
<application ...>
    ...
    <service
        android:name=".fcm.MyFirebaseMessagingService"
        android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>

    <!-- Set custom default icon. This is used when no icon is set in the notification payload. -->
    <meta-data
        android:name="com.google.firebase.messaging.default_notification_icon"
        android:resource="@android:drawable/ic_dialog_info" /> <!-- TODO: Replace with your own icon -->
    ...
</application>
```

With this setup, you can send test notifications from the Firebase Console's "Engage" > "Messaging" section. When the app is in the background, notifications are handled automatically by the system. When it's in the foreground, `onMessageReceived` is called, giving you full control over how to present the notification.

---

### H. Navigation

Structure the app's navigation using Jetpack Compose's Navigation component.

```kotlin
// app/src/main/java/com/bookwave/navigation/AppNavHost.kt
package com.bookwave.navigation // TODO: Replace with your package name

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bookwave.ui.screen.ChatbotScreen
import com.bookwave.ui.screen.LoginScreen
import com.bookwave.ui.screen.ProfileScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                },
                onNavigateToSignUp = { navController.navigate("signup") }
            )
        }
        composable("signup") { /* SignUpScreen composable */ }
        composable("home") { /* HomeScreen composable */ }
        composable("player/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            // PlayerScreen(bookId = bookId)
        }
        composable("chat") { /* ChatScreen composable */ }
        composable("ai_chatbot") { ChatbotScreen() }
        composable("profile") { ProfileScreen() }
    }
}
```

This `NavHost` acts as the central hub for your app's screens. The `startDestination` defines the first screen shown. Using `navController.navigate("route")` triggers a screen change. The `popUpTo` action in the `onLoginSuccess` lambda is important—it clears the login screen from the back stack, so the user can't navigate back to it after logging in.

---

### I. State Management & Architecture

We've consistently followed the modern Android architecture pattern recommended by Google: **UI Layer → ViewModel → Repository → Data Source**.

- **UI Layer (Composables)**: Its only job is to display state and pass user events up to the ViewModel. It should be as "dumb" as possible.
- **ViewModel**: Holds and manages UI-related state. It survives configuration changes (like screen rotation) and exposes state to the UI via `StateFlow`.
- **Repository**: The single source of truth for data. It abstracts away the origin of the data (Firebase, a network API, a local database) and provides a clean API for the ViewModels to consume.
- **Data Source**: The actual provider of data, such as Firebase's Auth, Firestore, and Storage services.

A typical project structure reflecting this architecture would look like this:

```
com.bookwave
├── data
│   ├── model          // UserProfile.kt, Book.kt, Message.kt
│   ├── repository     // FirestoreRepository.kt, AiRepository.kt
│   └── di             // FirebaseModule.kt
├── ui
│   ├── screen         // LoginScreen.kt, PlayerScreen.kt, ChatScreen.kt
│   └── components     // Reusable composables like MessageBubble.kt
├── viewmodel          // AuthViewModel.kt, ChatViewModel.kt, AiViewModel.kt
├── navigation         // AppNavHost.kt
├── fcm                // MyFirebaseMessagingService.kt
├── player             // PlayerManager.kt
└── BookWaveApplication.kt
```

---

## Conclusions

In this tutorial, you learned how to build a complete Android application using Jetpack Compose and Firebase, including authentication, real-time data storage, and UI state management.

We explored how Compose allows developers to build clean, declarative UI components, and how Firebase simplifies backend logic with ready-to-use services such as Authentication and Firestore.

By completing this tutorial, you should now understand:

- How to structure a modern Android app using Jetpack Compose
- How to connect your project to Firebase
- How to implement authentication flows (sign up, login, logout)
- How to read and write data using Firestore
- How to organize your ViewModel, UI, and business logic cleanly
- How to use state management effectively with Compose

There are many other ways to approach the same functionality.

For example:
- Instead of Firebase Authentication, you could use Auth0 or Supabase Auth
- Instead of Firestore, you could use Room, MongoDB Atlas, or Appwrite
- For UI, you could combine Compose with Material3 components, or use third-party UI libraries

If you want to go further, consider exploring:
- Firebase Cloud Messaging (push notifications)
- Firebase Storage (uploading media)
- Offline-first architecture with Room + Firestore Sync
- MVVM + Clean Architecture for large-scale apps

---

## See Also

Below is a list of high-quality tutorials, videos, and documentation that were useful during the development of this project:

### Jetpack Compose

- [Jetpack Compose Official Docs](https://developer.android.com/jetpack/compose)
- [Compose Navigation Guide](https://developer.android.com/jetpack/compose/navigation)
- [State in Compose](https://developer.android.com/jetpack/compose/state)

### Firebase

- [Firebase for Android Documentation](https://firebase.google.com/docs/android/setup)
- [Firebase Authentication (Email/Password)](https://firebase.google.com/docs/auth/android/start)
- [Cloud Firestore Quickstart](https://firebase.google.com/docs/firestore/quickstart)

### Android Studio

- [Android Studio Setup & Installation](https://developer.android.com/studio)

### Recommended Tutorials & Videos

- [Philip Lackner – Modern Android & Compose Tutorials](https://www.youtube.com/@PhilippLackner)
- [Android Developers YouTube Channel](https://www.youtube.com/c/AndroidDevelopers)

These resources will help you dig deeper into the concepts used in this project and continue improving your Android development skills.

---

**Built with ❤️ using Android Studio, Kotlin, Jetpack Compose, and Firebase**
