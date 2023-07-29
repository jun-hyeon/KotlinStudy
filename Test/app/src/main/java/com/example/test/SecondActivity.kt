package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.adapter.CountAdapter
import com.example.test.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private val binding : ActivitySecondBinding by lazy {
        ActivitySecondBinding.inflate(layoutInflater)
    }

    private lateinit var countAdapter : CountAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setRv()
    }

    private fun setRv(){
         val list = mutableListOf<Int>()

        for(i in 1..100){
            list.add(i)
        }
        countAdapter = CountAdapter(list)
        binding.recyclerView.apply {
            adapter = countAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }
}