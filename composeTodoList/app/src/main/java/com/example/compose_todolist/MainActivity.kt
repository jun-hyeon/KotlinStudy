package com.example.compose_todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.ColorTheme
import com.example.compose_todolist.domain.util.TodoAndroidViewModelFactory
import com.example.compose_todolist.ui.main.MainScreen
import com.example.compose_todolist.ui.main.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = TodoAndroidViewModelFactory(application),
            )
            ColorTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface

                ) {
                    MainScreen(viewModel = viewModel)
                }

            }
        }
    }
}


