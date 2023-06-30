package com.example.msololife.board

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.msololife.R
import com.example.msololife.utils.FBAuth
import java.util.zip.Inflater

class BoardListRVAdapter(private val boardModelList : MutableList<BoardModel>): RecyclerView.Adapter<BoardListRVAdapter.ViewHolder>() {

    interface ItemClick{
        fun onClick(view : View , position: Int)
    }
    var itemClick : ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.board_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return boardModelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (itemClick != null){
            holder.itemView.setOnClickListener{
                itemClick?.onClick(it, position)
            }
        }



        holder.bind(boardModelList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val itemLinearLayoutView = itemView.findViewById<LinearLayout>(R.id.itemView)
        private val titleArea: TextView = itemView.findViewById(R.id.listTitleArea)
        private val contentArea : TextView = itemView.findViewById(R.id.listContentArea)
        private val timeArea : TextView = itemView.findViewById(R.id.listTimeArea)

        fun bind(item : BoardModel){

            if(item.uid == FBAuth.getUid()){
                itemLinearLayoutView.setBackgroundColor(Color.parseColor("#ffa500"))
            }

            titleArea.text = item.title
            contentArea.text = item.content
            timeArea.text = item.time


        }
    }
}