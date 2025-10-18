package edu.gvsu.cis.bookwave.data.model

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val profileImageUrl: String? = null,
)
