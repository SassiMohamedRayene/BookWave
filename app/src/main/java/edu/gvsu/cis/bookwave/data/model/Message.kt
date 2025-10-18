package edu.gvsu.cis.bookwave.data.model

data class Message(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val content: String,
    val timestamp: Long,
    val isRead: Boolean = false
)

data class Conversation(
    val user: User,
    val lastMessage: Message?,
    val unreadCount: Int = 0
)

//  FAKE data
object FakeUserData {
    val currentUser = User(
        id = 1,
        username = "You",
        email = "you@bookwave.com",
        password = "",
        profileImageUrl = null
    )

    val friends = listOf(
        User(
            id = 2,
            username = "Sarah Johnson",
            email = "sarah@email.com",
            password = "",
            profileImageUrl = null
        ),
        User(
            id = 3,
            username = "Michael Chen",
            email = "michael@email.com",
            password = "",
            profileImageUrl = null
        ),
        User(
            id = 4,
            username = "Emma Williams",
            email = "emma@email.com",
            password = "",
            profileImageUrl = null
        ),
        User(
            id = 5,
            username = "James Brown",
            email = "james@email.com",
            password = "",
            profileImageUrl = null
        ),
        User(
            id = 6,
            username = "Olivia Davis",
            email = "olivia@email.com",
            password = "",
            profileImageUrl = null
        ),
        User(
            id = 7,
            username = "Daniel Garcia",
            email = "daniel@email.com",
            password = "",
            profileImageUrl = null
        )
    )

    // Messages fake conversations
    val fakeMessages = mutableMapOf(
        2 to mutableListOf(
            Message(1, 2, 1, "Hey! Have you finished listening to '1984'?", System.currentTimeMillis() - 3600000, true),
            Message(2, 1, 2, "Yes! It was amazing. The narration was perfect.", System.currentTimeMillis() - 3500000, true),
            Message(3, 2, 1, "I know right! What are you listening to now?", System.currentTimeMillis() - 1800000, false)
        ),
        3 to mutableListOf(
            Message(4, 3, 1, "Do you have any sci-fi audiobook recommendations?", System.currentTimeMillis() - 7200000, true),
            Message(5, 1, 3, "Definitely check out 'Dune'! It's a masterpiece.", System.currentTimeMillis() - 7100000, true)
        ),
        4 to mutableListOf(
            Message(6, 4, 1, "Thanks for recommending 'Pride and Prejudice'!", System.currentTimeMillis() - 86400000, true)
        ),
        5 to mutableListOf(
            Message(7, 5, 1, "Want to join our book club?", System.currentTimeMillis() - 172800000, true),
            Message(8, 1, 5, "Sure! When do you meet?", System.currentTimeMillis() - 172700000, true),
            Message(9, 5, 1, "Every Friday at 7 PM", System.currentTimeMillis() - 172600000, false)
        ),
        6 to mutableListOf(
            Message(10, 6, 1, "Hi!", System.currentTimeMillis() - 259200000, true)
        ),
        7 to mutableListOf(
            Message(11, 7, 1, "I just started listening to 'The Hobbit'. So good!", System.currentTimeMillis() - 345600000, true)
        )
    )
}