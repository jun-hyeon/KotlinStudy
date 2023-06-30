package com.example.viewmodel_tutorial


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil

import com.example.viewmodel_tutorial.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
//    private val binding :ActivityMainBinding by lazy{     //viewBinding
//        ActivityMainBinding.inflate(layoutInflater)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(binding.root)                        //viewBinding

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)

        val factory = MyViewModelFactory(10, this)
        val myViewModel by viewModels<MyViewModel> { factory }

        binding.lifecycleOwner = this
        binding.viewmodel = myViewModel

        binding.textView.text = myViewModel.counter.toString()
        binding.button.setOnClickListener {
//            myViewModel.counter += 1
//            binding.textView.text = myViewModel.counter.toString()
//            myViewModel.saveState()
              myViewModel.liveCounter.value = myViewModel.liveCounter.value?.plus(1)
        }

        myViewModel.modifiedCounter.observe(this ){ counter ->
            binding.textView.text = counter.toString()
        }
    }
}