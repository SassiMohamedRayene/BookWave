package edu.gvsu.cis.bookwave.data

import edu.gvsu.cis.bookwave.R
import edu.gvsu.cis.bookwave.data.model.Book

object BooksData {
    val myBooks = listOf(
        Book(
            id = 1,
            title = "The Great Gatsby",
            author = "F. Scott Fitzgerald",
            description = "A classic American novel set in the Jazz Age, exploring themes of decadence, idealism, and social upheaval through the mysterious millionaire Jay Gatsby and his obsession with Daisy Buchanan.",
            coverImageUrl = "https://covers.openlibrary.org/b/id/7222246-L.jpg",
            audioUrl = "audio_gatsby.mp3",
            duration = 345,
            category = "Classic",
            rating = 4.5f,
            publishedYear = 1925,
            isFavorite = false,
            coverImageRes = R.drawable.coverbook
        ),
        Book(
            id = 2,
            title = "1984",
            author = "George Orwell",
            description = "A dystopian social science fiction novel and cautionary tale about totalitarianism, surveillance, and the dangers of absolute power in a world where truth is manipulated.",
            coverImageUrl = "https://covers.openlibrary.org/b/id/7222246-L.jpg",
            audioUrl = "audio_1984.mp3",
            duration = 420,
            category = "Dystopian",
            rating = 4.8f,
            publishedYear = 1949,
            isFavorite = false,
            coverImageRes = R.drawable.book_cover
        ),
        Book(
            id = 3,
            title = "To Kill a Mockingbird",
            author = "Harper Lee",
            description = "A gripping tale of racial injustice and childhood innocence in the American South during the 1930s, told through the eyes of young Scout Finch.",
            coverImageUrl = "https://covers.openlibrary.org/b/id/7222246-L.jpg",
            audioUrl = "audio_mockingbird.mp3",
            duration = 480,
            category = "Classic",
            rating = 4.7f,
            publishedYear = 1960,
            isFavorite = false,
            coverImageRes = R.drawable.coverbook2
        ),
        Book(
            id = 4,
            title = "Pride and Prejudice",
            author = "Jane Austen",
            description = "A romantic novel of manners that critiques the British landed gentry at the end of the 18th century, following Elizabeth Bennet and Mr. Darcy.",
            coverImageUrl = "https://covers.openlibrary.org/b/id/7222246-L.jpg",
            audioUrl = "audio_pride.mp3",
            duration = 540,
            category = "Romance",
            rating = 4.6f,
            publishedYear = 1813,
            isFavorite = false,
            coverImageRes = R.drawable.coverbook1
        ),
        Book(
            id = 5,
            title = "The Hobbit",
            author = "J.R.R. Tolkien",
            description = "A fantasy adventure about Bilbo Baggins, a hobbit who embarks on an unexpected quest to reclaim a treasure guarded by the dragon Smaug.",
            coverImageUrl = "https://covers.openlibrary.org/b/id/7222246-L.jpg",
            audioUrl = "audio_hobbit.mp3",
            duration = 600,
            category = "Fantasy",
            rating = 4.9f,
            publishedYear = 1937,
            isFavorite = false,
            coverImageRes = R.drawable.coverbook3
        )
    )
}
