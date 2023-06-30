package com.example.msololife.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.msololife.R

class CommentRVAdapter(private val commentModelList : MutableList<CommentModel>) : RecyclerView.Adapter<CommentRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentRVAdapter.ViewHolder, position: Int) {
        holder.bind(commentModelList[position])
    }

    override fun getItemCount(): Int {
        return commentModelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val commentTitle = itemView.findViewById<TextView>(R.id.commentListTitleArea)
        private val commentTime = itemView.findViewById<TextView>(R.id.commentListTimeArea)

        fun bind(item : CommentModel){
            commentTitle.text = item.commentTitle
            commentTime.text = item.commentCreatedTime
        }
    }
}