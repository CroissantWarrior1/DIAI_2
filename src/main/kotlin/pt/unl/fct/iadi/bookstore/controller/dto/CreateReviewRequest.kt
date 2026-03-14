package pt.unl.fct.iadi.bookstore.controller.dto

data class CreateReviewRequest(

    val rating: Int,

    val comment: String? = null
)