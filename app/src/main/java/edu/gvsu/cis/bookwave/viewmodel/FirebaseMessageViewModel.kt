package edu.gvsu.cis.bookwave.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import edu.gvsu.cis.bookwave.data.model.FirebaseConversation
import edu.gvsu.cis.bookwave.data.model.FirebaseMessage
import edu.gvsu.cis.bookwave.data.model.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirebaseMessageViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Liste des utilisateurs disponibles pour le chat
    private val _availableUsers = MutableStateFlow<List<FirebaseUser>>(emptyList())
    val availableUsers: StateFlow<List<FirebaseUser>> = _availableUsers.asStateFlow()

    // Liste des conversations
    private val _conversations = MutableStateFlow<List<FirebaseConversation>>(emptyList())
    val conversations: StateFlow<List<FirebaseConversation>> = _conversations.asStateFlow()

    // Messages pour la conversation actuelle
    private val _currentMessages = MutableStateFlow<List<FirebaseMessage>>(emptyList())
    val currentMessages: StateFlow<List<FirebaseMessage>> = _currentMessages.asStateFlow()

    // Utilisateur avec qui on discute actuellement
    private val _currentChatUser = MutableStateFlow<FirebaseUser?>(null)
    val currentChatUser: StateFlow<FirebaseUser?> = _currentChatUser.asStateFlow()

    // État de chargement
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Listeners pour le temps réel
    private var messagesListener: ListenerRegistration? = null
    private var conversationsListener: ListenerRegistration? = null

    init {
        loadAvailableUsers()
        startListeningToConversations()
    }

    /**
     * Charger tous les utilisateurs disponibles (sauf l'utilisateur actuel)
     */
    fun loadAvailableUsers() {
        val currentUserId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                _isLoading.value = true
                val snapshot = firestore.collection("users")
                    .get()
                    .await()

                val users = snapshot.documents.mapNotNull { doc ->
                    val user = FirebaseUser(
                        uid = doc.id,
                        username = doc.getString("username") ?: "",
                        email = doc.getString("email") ?: "",
                        profileImageUrl = doc.getString("profileImageUrl") ?: ""
                    )
                    if (user.uid != currentUserId) user else null
                }

                _availableUsers.value = users
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Écouter les conversations en TEMPS RÉEL
     * OPTIMISATION MAJEURE : Utilisation de addSnapshotListener au lieu de get()
     */
    private fun startListeningToConversations() {
        val currentUserId = auth.currentUser?.uid ?: return

        // Arrêter l'ancien listener si existant
        conversationsListener?.remove()

        // Écouter TOUS les messages en temps réel
        conversationsListener = firestore.collection("messages")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    error.printStackTrace()
                    return@addSnapshotListener
                }

                snapshot?.let { querySnapshot ->
                    viewModelScope.launch {
                        try {
                            // Filtrer les messages où l'utilisateur est impliqué
                            val allMessages = querySnapshot.documents.mapNotNull { doc ->
                                val message = FirebaseMessage(
                                    id = doc.id,
                                    senderId = doc.getString("senderId") ?: "",
                                    receiverId = doc.getString("receiverId") ?: "",
                                    content = doc.getString("content") ?: "",
                                    timestamp = doc.getLong("timestamp") ?: 0L,
                                    isRead = doc.getBoolean("isRead") ?: false
                                )

                                // Ne garder que les messages impliquant l'utilisateur actuel
                                if (message.senderId == currentUserId || message.receiverId == currentUserId) {
                                    message
                                } else {
                                    null
                                }
                            }

                            // Grouper par utilisateur de conversation
                            val conversationMap = mutableMapOf<String, MutableList<FirebaseMessage>>()

                            allMessages.forEach { message ->
                                val otherUserId = if (message.senderId == currentUserId) {
                                    message.receiverId
                                } else {
                                    message.senderId
                                }

                                conversationMap.getOrPut(otherUserId) { mutableListOf() }.add(message)
                            }

                            // Créer les conversations
                            val conversations = conversationMap.map { (userId, messages) ->
                                // Charger les infos utilisateur (en cache si possible)
                                val userDoc = firestore.collection("users").document(userId).get().await()
                                val user = FirebaseUser(
                                    uid = userId,
                                    username = userDoc.getString("username") ?: "",
                                    email = userDoc.getString("email") ?: "",
                                    profileImageUrl = userDoc.getString("profileImageUrl") ?: ""
                                )

                                val sortedMessages = messages.sortedBy { it.timestamp }
                                val lastMessage = sortedMessages.lastOrNull()
                                val unreadCount = messages.count {
                                    !it.isRead && it.senderId == userId
                                }

                                FirebaseConversation(
                                    user = user,
                                    lastMessage = lastMessage,
                                    unreadCount = unreadCount
                                )
                            }.sortedByDescending { it.lastMessage?.timestamp ?: 0 }

                            _conversations.value = conversations

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
    }

    /**
     * Charger les messages pour une conversation spécifique avec écoute en TEMPS RÉEL
     * OPTIMISATION : Requête plus précise avec whereIn
     */
    fun loadMessagesForUser(userId: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                // Charger les informations de l'utilisateur
                val userDoc = firestore.collection("users").document(userId).get().await()
                _currentChatUser.value = FirebaseUser(
                    uid = userId,
                    username = userDoc.getString("username") ?: "",
                    email = userDoc.getString("email") ?: "",
                    profileImageUrl = userDoc.getString("profileImageUrl") ?: ""
                )

                // Arrêter l'ancien listener si existant
                messagesListener?.remove()

                // OPTIMISATION : Deux listeners pour une synchronisation instantanée
                // Listener 1 : Messages envoyés par moi à cet utilisateur
                val listener1 = firestore.collection("messages")
                    .whereEqualTo("senderId", currentUserId)
                    .whereEqualTo("receiverId", userId)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            error.printStackTrace()
                            return@addSnapshotListener
                        }
                        updateMessages(userId, currentUserId)
                    }

                // Listener 2 : Messages reçus de cet utilisateur
                val listener2 = firestore.collection("messages")
                    .whereEqualTo("senderId", userId)
                    .whereEqualTo("receiverId", currentUserId)
                    .orderBy("timestamp", Query.Direction.ASCENDING)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            error.printStackTrace()
                            return@addSnapshotListener
                        }
                        updateMessages(userId, currentUserId)
                    }

                // Garder une référence pour nettoyer plus tard
                messagesListener = listener1 // On garde une référence principale

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Mettre à jour les messages pour la conversation actuelle
     */
    private fun updateMessages(otherUserId: String, currentUserId: String) {
        viewModelScope.launch {
            try {
                // Récupérer tous les messages entre les deux utilisateurs
                val messages1 = firestore.collection("messages")
                    .whereEqualTo("senderId", currentUserId)
                    .whereEqualTo("receiverId", otherUserId)
                    .get()
                    .await()

                val messages2 = firestore.collection("messages")
                    .whereEqualTo("senderId", otherUserId)
                    .whereEqualTo("receiverId", currentUserId)
                    .get()
                    .await()

                val allMessages = (messages1.documents + messages2.documents)
                    .mapNotNull { doc ->
                        FirebaseMessage(
                            id = doc.id,
                            senderId = doc.getString("senderId") ?: "",
                            receiverId = doc.getString("receiverId") ?: "",
                            content = doc.getString("content") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0L,
                            isRead = doc.getBoolean("isRead") ?: false
                        )
                    }
                    .sortedBy { it.timestamp }

                _currentMessages.value = allMessages

                // Marquer les messages comme lus
                markMessagesAsRead(otherUserId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Envoyer un message
     * OPTIMISATION : Pas besoin de recharger, le listener fera le travail !
     */
    fun sendMessage(receiverId: String, content: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        if (content.isBlank()) return

        viewModelScope.launch {
            try {
                val message = hashMapOf(
                    "senderId" to currentUserId,
                    "receiverId" to receiverId,
                    "content" to content,
                    "timestamp" to System.currentTimeMillis(),
                    "isRead" to false
                )

                firestore.collection("messages")
                    .add(message)
                    .await()

                // Pas besoin de loadConversations() - le listener le fait automatiquement !

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Marquer les messages comme lus
     */
    private fun markMessagesAsRead(senderId: String) {
        val currentUserId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val unreadMessages = firestore.collection("messages")
                    .whereEqualTo("senderId", senderId)
                    .whereEqualTo("receiverId", currentUserId)
                    .whereEqualTo("isRead", false)
                    .get()
                    .await()

                unreadMessages.documents.forEach { doc ->
                    doc.reference.update("isRead", true)
                }

                // Pas besoin de recharger - le listener le fait !

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Rechercher des conversations
     */
    fun searchConversations(query: String) {
        // Pour la recherche, on filtre localement (plus rapide)
        viewModelScope.launch {
            if (query.isBlank()) {
                // Le listener principal se chargera de recharger
                startListeningToConversations()
                return@launch
            }

            val filtered = _conversations.value.filter { conversation ->
                conversation.user.username.contains(query, ignoreCase = true)
            }
            _conversations.value = filtered
        }
    }

    /**
     * Nettoyer les listeners lors de la destruction du ViewModel
     */
    override fun onCleared() {
        super.onCleared()
        messagesListener?.remove()
        conversationsListener?.remove()
    }
}