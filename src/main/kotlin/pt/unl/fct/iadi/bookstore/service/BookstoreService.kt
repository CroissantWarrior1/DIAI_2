package pt.unl.fct.iadi.bookstore.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.bookstore.controller.dto.CreateBookRequest
import pt.unl.fct.iadi.bookstore.domain.Book

import pt.unl.fct.iadi.bookstore.controller.dto.BookNotFoundException
import pt.unl.fct.iadi.bookstore.domain.Review

import pt.unl.fct.iadi.bookstore.controller.dto.CreateReviewRequest

// UUID
import java.util.UUID

@Service
class BookstoreService {

    var books: MutableMap<String, Book> = mutableMapOf()

    var reviews: MutableMap<String, List<String>> = mutableMapOf(
        "9780134685991" to listOf("Great book on Java best practices!", "A must-read for Java developers."),
        "9781491950357" to listOf("Excellent introduction to Kotlin.", "Very helpful for learning Kotlin.")
    )



    fun createBook(book: Book): Book {

        if (books.putIfAbsent(book.isbn, book) != null)
                throw BookAlreadyExistsException(book.isbn)

        return book
    }

    fun listBooks(): List<Book> =
        books.values.sortedBy { it.isbn }

    fun replaceBook(isbn: String, book: Book): Pair<Book, Boolean> {
        val created = books.put(isbn, book) == null
        return book to created
    }
    fun getBook(isbn: String): Book? =
        books[isbn]

    fun deleteBook(isbn: String) {
        if (books.remove(isbn) == null)
            throw BookNotFoundException(isbn)

    }

    fun getReviews(isbn: String): List<Review> {

        val reviews = reviews[isbn] ?: throw BookNotFoundException(isbn)
        return listOf(Review("9780134685991", 5,
            "Great book on Java best practices!"), Review("9780134685991", 4,
            "A must-read for Java developers."))
    }

    fun createReview(isbn: String, request: CreateReviewRequest): Review {
        getBook(isbn) ?: throw BookNotFoundException(isbn)

        val review = Review(
            id = UUID.randomUUID().toString(),
            rating = request.rating,
            comment = request.comment ?: ""
        )

        return review
    }

}