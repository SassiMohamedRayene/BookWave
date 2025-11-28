package edu.gvsu.cis.bookwave.data

import edu.gvsu.cis.bookwave.R
import edu.gvsu.cis.bookwave.data.model.Book

object BooksData {
    val myBooks = listOf(
        Book(
            id = 1,
            title = "Awakening",
            author = "Kate Chopin",
            description = "Owing to its highly personal content focused on feminine sexuality, this LibriVox edition was recorded by eight female readers. The Modern Library edition of The Awakening has an introduction by Kay Gibbons, who writes: “The Awakening shocked turn-of-the-century readers with its forthright treatment of sex and suicide. Departing from literary convention, Kate Chopin failed to condemn her heroine’s desire for an affair with the son of a Louisiana resort owner, whom she meets on vacation. The power of sensuality, the delusion of ecstatic love, and the solitude that accompanies the trappings of middle- and upper-class life are the themes of this now-classic novel.” – As Kay Gibbons points out, Chopin “was writing American realism before most Americans could bear to hear that they were living it.” To give you an idea of the subject matter, Project Gutenburg catalogues The Awakening under \"Adultery -- Fiction -- Women -- Louisiana -- New Orleans -- Social conditions.",
            coverImageUrl = "https://m.media-amazon.com/images/I/71FophEGCRL._AC_UF1000,1000_QL80_.jpg",
            audioUrl = "awakening.mp3",
            duration = 36,
            category = "Romance",
            rating = 4.0f,
            publishedYear = 1899,
            isFavorite = false,
            coverImageRes = R.drawable.awakeningcover
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
            title = "Art of War",
            author = "Sun Tzu",
            description = "The Art of War is a Chinese military treatise written during the 6th century BC by Sun Tzu. Composed of 13 chapters, each of which is devoted to one aspect of warfare, it has long been praised as the definitive work on military strategies and tactics of its time. The Art of War is one of the oldest and most famous studies of strategy and has had a huge influence on both military planning and beyond. The Art of War has also been applied, with much success, to business and managerial strategies.",
            coverImageUrl = "https://m.media-amazon.com/images/I/51no+g+ttWL._SL500_.jpg",
            audioUrl = "art_of_war.mp3",
            duration = 9,
            category = "War",
            rating = 4.5f,
            publishedYear = -500,
            isFavorite = false,
            coverImageRes = R.drawable.artofwar
        ),
        Book(
            id = 6,
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
