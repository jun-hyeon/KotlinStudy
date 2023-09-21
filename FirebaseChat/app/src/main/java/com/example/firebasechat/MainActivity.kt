package com.example.firebasechat

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.firebasechat.ui.theme.FirebaseChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FirebaseChatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    color = MaterialTheme.colorScheme.background,

                ) {
                   LoginScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var id by remember{mutableStateOf("")}
    var pwd by remember{mutableStateOf("")}
    Column (
        modifier = modifier
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = id,
            onValueChange = {
            id = it
            },
            label = {
                Text("ID를 입력해주세요.")
            }
        )

        Spacer(modifier = modifier.height(16.dp))

        TextField(
            modifier = modifier.fillMaxWidth(),
            value = pwd,
            onValueChange = {
                pwd = it
            },
            label = {
                Text("PWD를 입력해주세요.")
            }
        )

        Spacer(modifier = modifier.height(24.dp))

        Button(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,


            ),
            onClick = { /*TODO*/ }
        ){
            Text(
                text = "로그인",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            
        }

        Spacer(modifier = modifier.height(16.dp))

        Button(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            onClick = { /*TODO*/ }
            
        ){
            Text(
                text = "회원가입",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

        }
    }

}

@Composable
fun JoinScreen(modifier: Modifier = Modifier){

}

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    FirebaseChatTheme {
        LoginScreen(modifier = Modifier)
    }
}