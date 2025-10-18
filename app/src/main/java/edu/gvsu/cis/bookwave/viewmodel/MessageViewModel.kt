package edu.gvsu.cis.bookwave.viewmodel

import androidx.lifecycle.ViewModel
import edu.gvsu.cis.bookwave.data.model.Conversation
import edu.gvsu.cis.bookwave.data.model.FakeUserData
import edu.gvsu.cis.bookwave.data.model.Message
import edu.gvsu.cis.bookwave.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MessageViewModel : ViewModel() {

    // Liste des conversations
    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    // Messages pour un utilisateur spécifique
    private val _currentMessages = MutableStateFlow<List<Message>>(emptyList())
    val currentMessages: StateFlow<List<Message>> = _currentMessages.asStateFlow()

    // Utilisateur actuellement sélectionné pour le chat
    private val _currentChatUser = MutableStateFlow<User?>(null)
    val currentChatUser: StateFlow<User?> = _currentChatUser.asStateFlow()

    init {
        loadConversations()
    }

    // Charger toutes les conversations
    private fun loadConversations() {
        val conversations = FakeUserData.friends.map { friend ->
            val messages = FakeUserData.fakeMessages[friend.id] ?: emptyList()
            val lastMessage = messages.lastOrNull()
            val unreadCount = messages.count { !it.isRead && it.senderId == friend.id }

            Conversation(
                user = friend,
                lastMessage = lastMessage,
                unreadCount = unreadCount
            )
        }.sortedByDescending { it.lastMessage?.timestamp ?: 0 }

        _conversations.value = conversations
    }

    // Charger les messages pour un utilisateur spécifique
    fun loadMessagesForUser(userId: Int) {
        val user = FakeUserData.friends.find { it.id == userId }
        _currentChatUser.value = user

        val messages = FakeUserData.fakeMessages[userId] ?: emptyList()
        _currentMessages.value = messages

        // Marquer les messages comme lus
        markMessagesAsRead(userId)
    }

    // Envoyer un message
    fun sendMessage(receiverId: Int, content: String) {
        if (content.isBlank()) return

        val newMessage = Message(
            id = generateMessageId(),
            senderId = FakeUserData.currentUser.id,
            receiverId = receiverId,
            content = content,
            timestamp = System.currentTimeMillis(),
            isRead = false
        )

        // Ajouter le message à la liste
        val messages = FakeUserData.fakeMessages[receiverId]?.toMutableList() ?: mutableListOf()
        messages.add(newMessage)
        FakeUserData.fakeMessages[receiverId] = messages

        // Mettre à jour l'état
        _currentMessages.value = messages
        loadConversations()
    }

    // Marquer les messages comme lus
    private fun markMessagesAsRead(userId: Int) {
        val messages = FakeUserData.fakeMessages[userId]?.toMutableList() ?: return
        val updatedMessages = messages.map { message ->
            if (message.senderId == userId && !message.isRead) {
                message.copy(isRead = true)
            } else {
                message
            }
        }
        FakeUserData.fakeMessages[userId] = updatedMessages.toMutableList()
        loadConversations()
    }

    // Générer un ID unique pour les messages
    private fun generateMessageId(): Int {
        return FakeUserData.fakeMessages.values.flatten().maxOfOrNull { it.id }?.plus(1) ?: 1
    }

    // Rechercher des conversations
    fun searchConversations(query: String) {
        if (query.isBlank()) {
            loadConversations()
            return
        }

        val filtered = _conversations.value.filter { conversation ->
            conversation.user.username.contains(query, ignoreCase = true)
        }
        _conversations.value = filtered
    }
}