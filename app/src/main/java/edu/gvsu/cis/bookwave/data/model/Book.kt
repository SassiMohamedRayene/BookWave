package edu.gvsu.cis.bookwave.data.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val coverImageUrl: String,
    val audioUrl: String,
    val duration: Int,
    val category: String,
    val rating: Float = 0f,
    val publishedYear: Int,
    var isFavorite: Boolean = false,
    val coverImageRes: Int // AJOUT pour utiliser des ressources locales
)

