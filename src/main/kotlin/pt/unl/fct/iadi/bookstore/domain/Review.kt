package pt.unl.fct.iadi.bookstore.domain

import io.swagger.v3.oas.annotations.media.Schema
import pt.unl.fct.iadi.bookstore.controller.dto.ReviewResponse


data class Review(
    @field:Schema(
        description = "Unique identifier for the review",
        example = "123e4567-e89b-12d3-a456-426614174000"
    )
    val id: String,

    @field:Schema(
        description = "Rating of the book, must be between 1 and 5",
        example = "4",
        minimum = "1",
        maximum = "5"
    )
    val rating: Int,

    @field:Schema(
        description = "Optional comment about the book, max 500 characters",
        example = "Great book on Kotlin programming!",
        maxLength = 500
    )
    val comment: String,
)
{
    fun toResponse() = ReviewResponse(
        id,
        rating,
        comment
    )

}