package com.example.booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.databinding.ItemBookPreviewBinding

class BookSearchAdapter : ListAdapter<Book, BookSearchViewHolder>(BookDiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(
            ItemBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val book = currentList[position]
        holder.bind(book)
        holder.itemView.setOnClickListener {
           onItemClickListener?.let { it(book) }
//            itemClickListener.onClick(it, position, book)
        }
    }

    //강의에서 사용한 클릭 리스너
    private var onItemClickListener : ((book: Book) -> Unit?)? = null
    fun setOnItemClickListener(listener : (book: Book) -> Unit){
        onItemClickListener = listener
    }



    //인터넷 참고 클릭 리스너
    interface OnItemClickListener{        //인터페이스
        fun onClick( book: Book)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {       //프래그먼트에서 사용할 함수
        this.itemClickListener =  onItemClickListener
    }
    private lateinit var itemClickListener: OnItemClickListener               // 인터페이스 생성ㅌ


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
