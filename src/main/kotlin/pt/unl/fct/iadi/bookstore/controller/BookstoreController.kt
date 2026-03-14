package pt.unl.fct.iadi.bookstore.controller



import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import pt.unl.fct.iadi.bookstore.controller.dto.BookResponse
import pt.unl.fct.iadi.bookstore.controller.dto.CreateBookRequest
import pt.unl.fct.iadi.bookstore.domain.Book

import pt.unl.fct.iadi.bookstore.controller.dto.PatchBookRequest
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewResponse
import  pt.unl.fct.iadi.bookstore.domain.Review
import pt.unl.fct.iadi.bookstore.controller.dto.CreateReviewRequest


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

    override fun replaceBook(isbn: String, request: CreateBookRequest): ResponseEntity<Book> {
        val (book, created) = service.replaceBook(isbn, request.toBook())

        return if (created) {
            val location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri()
            ResponseEntity.created(location).body(book)
        } else {
            ResponseEntity.ok(book)
        }
    }


    override fun patchBook(isbn: String, request: PatchBookRequest): ResponseEntity<Book> {
        val existing = service.getBook(isbn) ?: throw IllegalArgumentException("Book with ISBN $isbn not found")

        val updated = existing.copy(
            title = request.title ?: existing.title,
            author = request.author ?: existing.author,
            price = request.price ?: existing.price,
            image = request.image ?: existing.image
        )

        service.replaceBook(isbn, updated)

        return ResponseEntity.ok(updated)
    }

    override fun deleteBook(isbn: String): ResponseEntity<Unit> {

        service.deleteBook(isbn)

        return ResponseEntity.noContent().build()
    }

    override fun listBookReviews(isbn: String): ResponseEntity<List<ReviewResponse>> {

        val reviews = service.getReviews(isbn).map { it.toResponse()}

        return ResponseEntity.ok(reviews)
    }

    override fun createReview(isbn: String, request: CreateReviewRequest): ResponseEntity<Unit> {
        val review = service.createReview(isbn, request)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(review.id)
            .toUri()
        return ResponseEntity.created(location).build()
    }

}

