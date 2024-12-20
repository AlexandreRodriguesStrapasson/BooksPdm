package com.example.bookspdm.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.bookspdm.R

class BookSqlite(context: Context): BookDao {

    companion object {
        private const val BOOK_DATABASE_FILE = "book"
        private const val BOOK_TABLE = "book"
        private const val TITLE_COLUMN = "title"
        private const val ISBN_COLUMN = "isbn"
        private const val FIRST_AUTHOR_COLUMN = "first_author"
        private const val PUBLISHER_COLUMN = "publisher"
        private const val EDITION_COLUMN = "edition"
        private const val PAGES_COLUMN = "pages"

        private const val CREATE_BOOK_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS $BOOK_TABLE (" +
                "$TITLE_COLUMN TEXT NOT NULL," +
                "$ISBN_COLUMN TEXT NOT NULL PRIMARY KEY, " +
                "$FIRST_AUTHOR_COLUMN TEXT NOT NULL," +
                "$PUBLISHER_COLUMN TEXT NOT NULL," +
                "$EDITION_COLUMN INTEGER NOT NULL," +
                "$PAGES_COLUMN INTEGER NOT NULL );"
    }

    private val bookDataBase: SQLiteDatabase = context.openOrCreateDatabase(
        BOOK_DATABASE_FILE,
        MODE_PRIVATE,
        null
    )
    init{
        try {
            bookDataBase.execSQL(CREATE_BOOK_TABLE_STATEMENT)
        }
        catch (es: Exception){
            Log.e(context.getString(R.string.app_name), es.toString())
        }
    }

    override fun creationBook(book: Book): Long =
        bookDataBase.insert(BOOK_TABLE, null, bookContentValues(book))


    override fun updateBook(book: Book) = bookDataBase.update(
            BOOK_TABLE,
            bookContentValues(book),
            "$ISBN_COLUMN = ?",
            arrayOf(book.ispn)
    )


    override fun retriveBook(isbn: String): Book {
        //val curso = bookDataBase.query("SELECT * FROM $BOOK_TABLE WHERE $ISBN_COLUMN = ?")
        val cursor = bookDataBase.query(
            true,
            BOOK_TABLE,
            null,
            "$ISBN_COLUMN = ?",
            arrayOf(isbn),
            null,
            null,
            null,
            null
        )

        return if(cursor.moveToFirst()){
            cursorToBook(cursor)
        }else{
            Book()
        }
    }

    override fun retriveBooks(): MutableList<Book> {
        val bookList = mutableListOf<Book>()

        val cursor = bookDataBase.rawQuery("SELECT * FROM $BOOK_TABLE WHERE $ISBN_COLUMN = ?", null)
        while(cursor.moveToFirst()){
            bookList.add(cursorToBook(cursor))
        }

        return bookList
    }

    override fun deletionBook(isbn: String) = bookDataBase.delete(
        BOOK_TABLE,
        "$ISBN_COLUMN = ?",
        arrayOf(isbn)
    )

    private fun bookContentValues(book: Book): ContentValues = ContentValues().apply {
        with(book){
            put(TITLE_COLUMN, title)
            put(ISBN_COLUMN, ispn)
            put(FIRST_AUTHOR_COLUMN, firstAuthor)
            put(PUBLISHER_COLUMN, publisher)
            put(EDITION_COLUMN, edition)
            put(PAGES_COLUMN, pages)
        }
    }

    private fun cursorToBook(cursor: Cursor) = with(cursor){
        Book(
            getString(getColumnIndexOrThrow(TITLE_COLUMN)),
            getString(getColumnIndexOrThrow(ISBN_COLUMN)),
            getString(getColumnIndexOrThrow(FIRST_AUTHOR_COLUMN)),
            getString(getColumnIndexOrThrow(PUBLISHER_COLUMN)),
            getInt(getColumnIndexOrThrow(EDITION_COLUMN)),
            getInt(getColumnIndexOrThrow(PAGES_COLUMN)),
        )
    }

}