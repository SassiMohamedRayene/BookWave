package edu.gvsu.cis.bookwave.data.model

data class FirebaseMessage(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val content: String = "",
    val timestamp: Long = 0L,
    val isRead: Boolean = false
)

data class FirebaseUser(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val profileImageUrl: String = ""
)

data class FirebaseConversation(
    val user: FirebaseUser,
    val lastMessage: FirebaseMessage?,
    val unreadCount: Int = 0
)