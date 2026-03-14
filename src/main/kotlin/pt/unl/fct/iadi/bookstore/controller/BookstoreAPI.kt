package pt.unl.fct.iadi.bookstore.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Schema

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import pt.unl.fct.iadi.bookstore.controller.dto.CreateBookRequest

import pt.unl.fct.iadi.bookstore.domain.Book
import pt.unl.fct.iadi.bookstore.controller.dto.BookResponse
import pt.unl.fct.iadi.bookstore.controller.dto.PatchBookRequest

@Tag(name = "Bookstore API", description = "the bookstore API")
interface BookstoreAPI {

    @Operation(
        summary = "Get a list of all books",
        operationId = "listBooks",
        tags = ["book"]
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "List of books retrieved successfully — empty array when no books exist",
            content = [Content(
                mediaType = "application/json",
                array = ArraySchema(schema = Schema(implementation = BookResponse::class))
            )]
        )
    )
    @RequestMapping(
        value = ["/books"],
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun listBooks(): ResponseEntity<List<BookResponse>>

    @Operation(summary = "Create a new book", operationId = "createBook")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Book created",
            headers = [Header(name = "Location", description = "URI of the created book",
                schema = Schema(type = "string", format = "uri"))],
            content = [Content(schema = Schema(hidden = true))]),
        ApiResponse(responseCode = "400", description = "Validation error",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "409", description = "Book with this ISBN already exists",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @RequestMapping(
        value = ["/books"],
        consumes = ["application/json"],
        method = [RequestMethod.POST]
    )
    fun createBook(@Valid @RequestBody request: CreateBookRequest): ResponseEntity<Unit>


    @Operation(
        summary = "Get a single book by its ISBN",
        operationId = "getBook",
        tags = ["book"]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book found"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        produces = ["application/json"],
        method = [RequestMethod.GET]
    )
    fun getBook(
        @Parameter(description = "ISBN of the book to retrieve", required = true)
        @PathVariable isbn: String
    ): ResponseEntity<Book>

//    Replace a book
    @Operation(
        summary = "Replace an existing book",
        operationId = "replaceBook",
        tags = ["book"]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book replaced successfully"),
            ApiResponse(responseCode = "201", description = "Book created as it did not exist"),
            ApiResponse(responseCode = "400", description = "Validation error")
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        consumes = ["application/json"],
        produces = ["application/json"],
        method = [RequestMethod.PUT]
    )
    fun replaceBook(
        @Parameter(description = "ISBN of the book to replace", required = true)
        @PathVariable isbn: String,
        @Valid @RequestBody request: CreateBookRequest
    ): ResponseEntity<Book>


    @Operation(
        summary = "Partially update a book",
        operationId = "patchBook",
        tags = ["book"]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Book updated successfully"),
            ApiResponse(responseCode = "400", description = "Validation error"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        consumes = ["application/json"],
        produces = ["application/json"],
        method = [RequestMethod.PATCH]
    )
    fun patchBook(
        @Parameter(description = "ISBN of the book to update", required = true)
        @PathVariable isbn: String,
        @Valid @RequestBody request: PatchBookRequest
    ): ResponseEntity<Book>

    @Operation(
        summary = "Delete a book by its ISBN",
        operationId = "deleteBook",
        tags = ["book"]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Book deleted successfully"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
    @RequestMapping(
        value = ["/books/{isbn}"],
        method = [RequestMethod.DELETE]
    )
    fun deleteBook(
        @Parameter(description = "ISBN of the book to delete", required = true)
        @PathVariable isbn: String
    ): ResponseEntity<Unit>



}