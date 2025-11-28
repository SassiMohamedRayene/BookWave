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
            title = "Years After",
            author = "Alexandre Dumas",
            description = "Twenty Years After (French: Vingt ans après) is a novel by Alexandre Dumas, first serialized from January to August 1845. A book of The d'Artagnan Romances, it is a sequel to The Three Musketeers (1844) and precedes the 1847–1850 novel The Vicomte de Bragelonne (which includes the sub-plot Man in the Iron Mask).",
            coverImageUrl = "https://m.media-amazon.com/images/I/71EsE1cGTcL._AC_UF1000,1000_QL80_.jpg",
            audioUrl = "twentyyearsafter.mp3",
            duration = 24,
            category = "adventure",
            rating = 4.1f,
            publishedYear = 1845,
            isFavorite = false,
            coverImageRes = R.drawable.twentyyearsafter
        ),
        Book(
            id = 3,
            title = "Ivanhoe",
            author = "Sir Walter Scott",
            description = "Follows the fortunes of the son of a noble Saxon family in Norman England as he woos his lady, disobeys his father, and is loved by another. Set in late 12C England and in Palestine with Richard Cœur-de-Lion at the Crusades, it's another ripping historical yarn by Scott",
            coverImageUrl = "https://m.media-amazon.com/images/I/81bwKfkpbBL._AC_UF1000,1000_QL80_.jpg",
            audioUrl = "ivanhoe.mp3",
            duration = 25,
            category = "adventure",
            rating = 4.0f,
            publishedYear = 1819,
            isFavorite = false,
            coverImageRes = R.drawable.ivanhoecover
        ),
        Book(
            id = 4,
            title = "Oliver Twist",
            author = "Charles Dickens",
            description = "Oliver Twist is an 1838 novel by Charles Dickens. It was originally published as a serial. Like most of Dickens' work, the book is used to call the public's attention to various contemporary social evils, including the workhouse, child labour and the recruitment of children as criminals. The novel is full of sarcasm and dark humour, even as it treats its serious subject, revealing the hypocrisies of the time. It has been the subject of numerous film and television adaptations, and the basis for a highly successful British musical, Oliver!.",
            coverImageUrl = "https://m.media-amazon.com/images/I/71xoeW8+ebL._AC_UF1000,1000_QL80_.jpg",
            audioUrl = "oliver_twist.mp3",
            duration = 8,
            category = "Classic",
            rating = 3.9f,
            publishedYear = 1838,
            isFavorite = false,
            coverImageRes = R.drawable.olivertwist
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
            title = "Wuthering Heights",
            author = "Emily Brontë",
            description = "A tale of passion set in the bleak Yorkshire moors in mid 19thC, far from the Victorian uprightness, Wuthering Heights depicts the mutual love of Catherine and Heathcliff till destruction rends the narration; yet cruelty is only to be met with forgiveness in the following generations. Romantic, impassioned and wild, it is also a dark journey in the human soul.",
            coverImageUrl = "https://fcs-img.s3.amazonaws.com/1ebda62b-7f02-4fab-8e39-a5e6016ae6b4/27289490-2bf9-40e4-b347-ad63012b464a/XL.jpg",
            audioUrl = "wutheringheights.mp3",
            duration = 13,
            category = "Romance",
            rating = 4.0f,
            publishedYear = 1847,
            isFavorite = false,
            coverImageRes = R.drawable.wutheringheights
        )
    )
}
