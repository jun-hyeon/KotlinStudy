package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.test2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController : NavController

    private val testViewModel: TestViewModel by viewModels<TestViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpNav()



    }

    private fun setUpNav(){
        val hostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = hostFragment.navController
        binding.bottomNavigationBar.setupWithNavController(navController)


    }
}