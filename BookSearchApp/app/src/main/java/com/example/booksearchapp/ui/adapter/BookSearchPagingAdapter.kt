package com.example.booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchPagingAdapter : PagingDataAdapter<Book, BookSearchViewHolder>(BookDiffCallback) {

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val pagedBook = getItem(position)
        pagedBook?.let { book ->
            holder.bind(book)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(book) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }


    //강의에서 사용한 클릭 리스너
    private var onItemClickListener : ((book: Book) -> Unit?)? = null
    fun setOnItemClickListener(listener : (book: Book) -> Unit) {
        onItemClickListener = listener
    }


    companion object{
        private val BookDiffCallback = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }

}