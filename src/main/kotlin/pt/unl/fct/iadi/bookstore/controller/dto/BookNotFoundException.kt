package pt.unl.fct.iadi.bookstore.controller.dto

class BookNotFoundException (isbn: String) : RuntimeException("Book with ISBN $isbn not found")
