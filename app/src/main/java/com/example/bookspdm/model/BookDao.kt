package com.example.bookspdm.model

interface  BookDao {
    fun creationBook(book: Book): Long
    fun updateBook(book: Book): Int
    fun retriveBook(isbn: String): Book
    fun retriveBooks(): MutableList<Book>
    fun deletionBook(isbn: String): Int
}