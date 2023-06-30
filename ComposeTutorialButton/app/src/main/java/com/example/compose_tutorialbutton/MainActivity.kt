package com.example.compose_tutorialbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_tutorialbutton.ui.theme.ComposeTutorialButtonTheme
import com.example.compose_tutorialbutton.ui.theme.Pink80
import com.example.compose_tutorialbutton.ui.theme.Purple40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val (text, setValue) = remember{
                mutableStateOf("")
            }
            val snackbarHostState = SnackbarHostState()
            val scope = rememberCoroutineScope()
            ComposeTutorialButtonTheme {
                // A surface container using the 'background' color from the theme
                Surface( color = MaterialTheme.colorScheme.background) {
                   Scaffold (
                            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                           ){it
                       Column (
                           modifier = Modifier.fillMaxSize(),
                           verticalArrangement = Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally,
                       ){
                           TextField(value = text, onValueChange = setValue
                           )
                           Button(
                               onClick = {
                                   scope.launch {
                                       snackbarHostState.showSnackbar("Hello $text")
                                   }
                               },
                               colors = ButtonDefaults.buttonColors(
                                   containerColor = Purple40, //버튼 색상
                                   contentColor = Color.White // 버튼 내부 컨텐츠 색상
                               ))
                           {
                               Text(text = "Click!")

                           }
                       }
                   }
                }
            }
        }
    }
}
