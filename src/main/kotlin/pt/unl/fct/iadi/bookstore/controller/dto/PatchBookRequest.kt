package pt.unl.fct.iadi.bookstore.controller.dto

import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class PatchBookRequest(
    @field:Size(min = 1, max = 255)
    val title: String? = null,

    @field:Size(min = 1, max = 255)
    val author: String? = null,

    @field:Positive
    val price: Double? = null,

    @field:Size(min = 1, max = 500)
    val image: String? = null
)