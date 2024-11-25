package com.example.bookspdm.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.bookspdm.R
import com.example.bookspdm.databinding.ActivityMainBinding
import com.example.bookspdm.model.Book

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data source
    private val bookList: MutableList<Book> = mutableListOf()

    //adapter
    private val bookAdapter: ArrayAdapter<String> by lazy{
        val bookTitleList: MutableList<String> = mutableListOf()
        /*bookList.forEach{book -> bookTitleList.add(book.title)}
        //bookList.forEach{book -> bookTitleList.add(book.toString())}
        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            bookTitleList
        )*/

        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            bookList.run {
                bookList.forEach{book -> bookTitleList.add(book.title)}
                this.forEach { bookTitleList.add(it.title) }
                bookTitleList
            }
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        amb.toolbarInc.toolbar.let {
            it.subtitle = "Book List"
            setSupportActionBar(it)
        }

        fillBookList()

        amb.booksLv.adapter = bookAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.addBook -> {
            //abrir tela para adicionar nova linha

            true
        }
        else -> {
            false
        }
    }

    private fun fillBookList(){
        for(index in 1..50){
            bookList.add(
                Book(
                    "Title $index",
                    "ISBN: $index",
                    "Autor: $index",
                    "Publisher: $index",
                    index,
                    index
                )
            )
        }
    }
}