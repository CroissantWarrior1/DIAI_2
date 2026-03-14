package pt.unl.fct.iadi.bookstore.controller.dto

import java.util.*

data class ReviewResponse(
    val id: String,
    val rating: Int,
    val comment: String
)
