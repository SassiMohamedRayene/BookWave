package edu.gvsu.cis.bookwave.data.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val profileImageUrl: String,
    val favoriteGenres: List<String> = emptyList(),
    val listeningTime: Int = 0 // en heures
)
