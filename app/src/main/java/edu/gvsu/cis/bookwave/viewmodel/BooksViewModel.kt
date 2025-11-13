package edu.gvsu.cis.bookwave.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.gvsu.cis.bookwave.data.BooksData
import edu.gvsu.cis.bookwave.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {

    private val _books = MutableStateFlow(BooksData.myBooks.toMutableList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    // Liste des livres favoris
    val favoriteBooks: StateFlow<List<Book>> = MutableStateFlow(
        _books.value.filter { it.isFavorite }
    )

    init {
        // Observer les changements et mettre à jour les favoris
        viewModelScope.launch {
            _books.collect { booksList ->
                (favoriteBooks as MutableStateFlow).value = booksList.filter { it.isFavorite }
            }
        }
    }

    /**
     * Basculer le statut favori d'un livre
     */
    fun toggleFavorite(bookId: Int) {
        viewModelScope.launch {
            val updatedBooks = _books.value.map { book ->
                if (book.id == bookId) {
                    book.copy(isFavorite = !book.isFavorite)
                } else {
                    book
                }
            }
            _books.value = updatedBooks.toMutableList()
        }
    }

    /**
     * Obtenir un livre par son ID
     */
    fun getBookById(bookId: Int): Book? {
        return _books.value.find { it.id == bookId }
    }

    /**
     * Vérifier si un livre est favori
     */
    fun isFavorite(bookId: Int): Boolean {
        return _books.value.find { it.id == bookId }?.isFavorite ?: false
    }
}