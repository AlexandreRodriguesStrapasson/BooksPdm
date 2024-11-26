package com.example.bookspdm.ui

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bookspdm.R
import com.example.bookspdm.databinding.TileBookBinding
import com.example.bookspdm.model.Book

class BookAdapter(
    context: Context,
    private val bookList: MutableList<Book>
    ): ArrayAdapter<Book>(context, R.layout.tile_book, bookList) {
        private data class BookTileHolder(
            val titleTv: TextView,
            val firstAuthorTv: TextView,
            val editionTv: TextView
        )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        lateinit var tbb: TileBookBinding

        //Pegar um livro que vai ser usado apar preencher a célula
        val book = bookList[position]

        //Verificando se a célula já foi inflada ou não
        var bookTile = convertView
        if(bookTile == null){
            //se não foi, inflar uma nova célula
            tbb = TileBookBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            bookTile = tbb.root

            //Criar um holder para a nova célula
            val newBookTileHolder = BookTileHolder(tbb.titleTv, tbb.firstAuthorTv, tbb.editionTv)

            //Associar o Holder a célula
            bookTile.tag = newBookTileHolder

        }

        //Preenche os valore da célula com o novo livro
        val holder = bookTile?.tag as BookTileHolder
        holder.let{
            it.titleTv.text = book.title
            it.firstAuthorTv.text = book.firstAuthor
            it.editionTv.text = book.edition.toString()
        }

        //Retorna a célula preenchida
        return bookTile
    }
}