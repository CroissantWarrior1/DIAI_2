package pt.unl.fct.iadi.bookstore.controller.dto

import pt.unl.fct.iadi.bookstore.domain.Book
data class BookResponse(
    var isbn: String,
    val title: String,
    val author: String,
    val price: Double,
    val image: String
)

