package com.example.bookspdm.controller

import com.example.bookspdm.model.Book
import com.example.bookspdm.model.BookDao
import com.example.bookspdm.model.BookSqlite
import com.example.bookspdm.ui.MainActivity

class MainController (mainActivity: MainActivity){
    private val bookDao: BookDao = BookSqlite(mainActivity)

    fun insertBook(book: Book) = bookDao.creationBook(book)
    fun getBook(isbn: String) = bookDao.retriveBook(isbn)
    fun getBooks() = bookDao.retriveBooks()
    fun updateBook(book: Book) = bookDao.updateBook(book)
    fun removeBook(isbn: String) = bookDao.deletionBook(isbn)
}