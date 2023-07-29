package com.example.booksearchapp.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.WorkManager
import com.example.booksearchapp.R
import com.example.booksearchapp.data.db.BookSearchDatabase
import com.example.booksearchapp.data.model.Book
import com.example.booksearchapp.data.repository.BookSearchRepositoryImpl
import com.example.booksearchapp.databinding.ActivityMainBinding

import com.example.booksearchapp.util.Constants.DATASTORE_NAME
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

//    lateinit var bookSearchViewModel : BookSearchViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

//    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)
//    private val workManager = WorkManager.getInstance(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUPBottomNavigationView()

//        val database = BookSearchDatabase.getInstance(this)
//        val bookSearchRepository = BookSearchRepositoryImpl(database, dataStore)
//        val factory = BookSearchViewModelProviderFactory(bookSearchRepository,this, workerManager =  workManager)
//        bookSearchViewModel = ViewModelProvider(this,factory)[BookSearchViewModel::class.java ]
    }

    private fun setUPBottomNavigationView(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.searchFragment, R.id.favoriteFragment, R.id.settingsFragment
            )
        )
    }
}