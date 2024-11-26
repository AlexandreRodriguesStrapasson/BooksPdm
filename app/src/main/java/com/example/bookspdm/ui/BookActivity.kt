package com.example.bookspdm.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookspdm.databinding.ActivityBookBinding
import com.example.bookspdm.model.Book
import com.example.bookspdm.model.Constant

class BookActivity : AppCompatActivity() {
    private val abb: ActivityBookBinding by lazy {
        ActivityBookBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(abb.root)

        val receiveBook = intent.getParcelableExtra<Book>(Constant.BOOK)
        receiveBook?.let {book ->
            with(abb){
                with(book){
                    titleEt.setText(title)
                    isbnEt.setText(ispn)
                    isbnEt.isEnabled = false
                    firstAuthorEt.setText(firstAuthor)
                    publisherEt.setText(publisher)
                    editionEt.setText(edition.toString())
                    pagesEt.setText(pages.toString())
                }
            }
        }

        abb.toolbarInc.toolbar.let{
            it.subtitle = if(receiveBook == null) "New Book" else "Edit Book"
            setSupportActionBar(it)
        }


        abb.run {
            saverBt.setOnClickListener{
                val newBook = Book(
                    titleEt.text.toString(),
                    isbnEt.text.toString(),
                    firstAuthorEt.text.toString(),
                    publisherEt.text.toString(),
                    editionEt.text.toString().toInt(),
                    pagesEt.text.toString().toInt()
                )
                Intent().apply {
                    putExtra(Constant.BOOK, newBook)
                    setResult(RESULT_OK, this)
                    finish()
                }
            }
        }
    }
}