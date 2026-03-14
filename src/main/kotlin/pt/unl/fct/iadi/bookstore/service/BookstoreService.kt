package pt.unl.fct.iadi.bookstore.service

import org.springframework.stereotype.Service
import pt.unl.fct.iadi.bookstore.controller.dto.CreateBookRequest
import pt.unl.fct.iadi.bookstore.domain.Book

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
//                throw BookAlreadyExistsException(book.isbn)
            throw IllegalArgumentException("Book with ISBN ${book.isbn} already exists")

//            add to list
        books[book.isbn] = book
        return book
    }

    fun listBooks(): List<Book> =
        books.values.sortedBy { it.isbn }
}