package pt.unl.fct.iadi.bookstore.domain

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal as Decimal
import pt.unl.fct.iadi.bookstore.controller.dto.BookResponse

data class Book(
    @field:Schema(
        description = "Unique ISBN identifier",
        example = "9780134685991",
        pattern = "^\\d{13}$"
    )
    val isbn: String,

    @field:Schema(
        description = "Title of the book",
        example = "Effective Kotlin",
        minLength = 1,
        maxLength = 120
    )
    val title: String,

    @field:Schema(
        description = "Author of the book",
        example = "Marcin Moskala",
        minLength = 1,
        maxLength = 80
    )
    val author: String,

    @field:Schema(
        description = "Price of the book, must be greater than zero",
        example = "29.99",
        exclusiveMinimum = true,
        minimum = "0"
    )
    val price: Double,

    @field:Schema(
        description = "URL pointing to the book cover image",
        example = "https://example.com/covers/effective-kotlin.jpg"
    )
    val image: String,
)
{
    fun  toResponse() = BookResponse(
        isbn,
        title,
        author,
        price,
        image
    )
}