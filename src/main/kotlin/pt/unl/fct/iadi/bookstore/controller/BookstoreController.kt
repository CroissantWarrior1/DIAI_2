package pt.unl.fct.iadi.bookstore.controller



import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import pt.unl.fct.iadi.bookstore.controller.dto.BookResponse
import pt.unl.fct.iadi.bookstore.controller.dto.CreateBookRequest
import pt.unl.fct.iadi.bookstore.domain.Book
import pt.unl.fct.iadi.bookstore.domain.Review

import pt.unl.fct.iadi.bookstore.service.BookstoreService
@RestController
class BookstoreController(private val service: BookstoreService) : BookstoreAPI {
    override fun listBooks(): ResponseEntity<List<BookResponse>> {
        val books = service.listBooks().map { it.toResponse() }
        return ResponseEntity.ok(books)
    }

    override fun createBook(request: CreateBookRequest): ResponseEntity<Unit> {
        val book = service.createBook(request.toBook())
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{isbn}")
            .buildAndExpand(book.isbn)
            .toUri()
        return ResponseEntity.created(location).build()
    }

    override fun getBook(isbn: String): ResponseEntity<Book> {
        TODO("Not yet implemented")
    }


}