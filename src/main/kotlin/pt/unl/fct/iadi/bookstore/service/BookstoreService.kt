package pt.unl.fct.iadi.bookstore.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.bookstore.controller.dto.CreateBookRequest
import pt.unl.fct.iadi.bookstore.domain.Book

import pt.unl.fct.iadi.bookstore.controller.dto.BookNotFoundException

@Service
class BookstoreService {

    var books: MutableMap<String, Book> = mutableMapOf(
        "9780134685991" to Book(
            isbn = "9780134685991",
            title = "Effective Java",
            author = "Joshua Bloch",
            price = 29.99,
            image = "https://example.com/cover.jpg"
        ),
        "9781491950357" to Book(
            isbn = "9781491950357",
            title = "Kotlin in Action",
            author = "Dmitry Jemerov and Svetlana Isakova",
            price = 39.99,
            image = "https://example.com/cover2.jpg"
        )
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
}