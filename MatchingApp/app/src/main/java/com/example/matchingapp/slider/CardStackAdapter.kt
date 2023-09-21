package com.example.matchingapp.slider

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.matchingapp.databinding.ItemCardBinding


class CardStackAdapter(private val items : List<String>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int  = items.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class MyViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data : String){

    }
}

