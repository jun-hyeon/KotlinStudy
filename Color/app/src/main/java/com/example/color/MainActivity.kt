package com.example.color

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.color.ui.theme.ColorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiary,


                ) {
                    Greeting()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting( modifier: Modifier = Modifier) {
    var name by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold (
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        topBar = { TopAppBar(
            title = { Text("Android")},
            colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) }

    ){
        Column(
            modifier = modifier

                .padding(it)

        ) {
            Text(
                text ="TEXT입니다. 테마가 적용되었을까요?",
                fontSize = 20.sp
                )
            OutlinedTextField(
                value = name,
                onValueChange = {text ->
                name = text },
                label = { Text(text = "아무 Text를 입력해주세요.")},
                singleLine = true,
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColorTheme {
        Greeting()
    }
}