package com.example.bookspdm.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bookspdm.R
import com.example.bookspdm.controller.MainController
import com.example.bookspdm.databinding.ActivityMainBinding
import com.example.bookspdm.model.Book
import com.example.bookspdm.model.Constant
import com.example.bookspdm.model.Constant.BOOK
import com.example.bookspdm.model.Constant.VIEW_MODE

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data source
    private val bookList: MutableList<Book> = mutableListOf()

    //adapter
    private val bookAdapter: BookAdapter by lazy{
        BookAdapter(this, bookList)
    }

    //Controller
    private val mainContrller: MainController by lazy {
        MainController(this)
    }

    private lateinit var barl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        barl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == RESULT_OK){
                val book = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra<Book>(Constant.BOOK)
                }
                else{
                    result.data?.getParcelableExtra<Book>(Constant.BOOK, Book::class.java)
                }
                book?.let{ receivedBook ->
                    val position = bookList.indexOfFirst { it.ispn == receivedBook.ispn }
                    if(position == -1){
                        bookList.add(receivedBook)
                    }else{
                        bookList[position] = receivedBook
                    }

                    bookAdapter.notifyDataSetChanged()
                }

            }
        }

        amb.toolbarInc.toolbar.let {
            it.subtitle = "Book List"
            setSupportActionBar(it)
        }

        fillBookList()

        amb.booksLv.adapter = bookAdapter

        amb.booksLv.setOnItemClickListener { _, _, postion, _ ->
            Intent(this, BookActivity::class.java).apply {
                putExtra(BOOK, bookList[postion])
                putExtra(VIEW_MODE, false)
                startActivity(this)
            }
        }

        registerForContextMenu(amb.booksLv)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.addBook -> {
            //abrir tela para adicionar nova linha
            barl.launch(Intent(this, BookActivity::class.java))
            true
        }
        else -> {
            false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) = menuInflater.inflate(R.menu.context_menu_main, menu)

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position

        return when(item.itemId){
            R.id.editBookMi -> {
                //Chamar tela de edição de livro
                Intent(this, BookActivity::class.java).apply {
                    putExtra(BOOK, bookList[position])
                    barl.launch(this)
                }
                true
            }
            R.id.removeBookMi -> {
                //Remover livro da lista
                bookList.removeAt(position)
                bookAdapter.notifyDataSetChanged()
                true
            }
            else -> {
                false
            }
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