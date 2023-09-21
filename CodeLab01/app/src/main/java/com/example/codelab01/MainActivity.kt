package com.example.codelab01

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codelab01.ui.theme.CodeLab01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeLab01Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000){"$it"}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(names){name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(targetValue = if (expanded.value) 48.dp else 0.dp,
        label = "extraPadding",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        color = MaterialTheme.colorScheme.primary
    ){
        Row(
            modifier = modifier.padding(24.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "hello,")
                Text(text = name, style = MaterialTheme.typography.headlineMedium)

            }
            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text( if(expanded.value) "Show less" else "Show more")
            }
        }
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)

@Composable
fun GreetingPreview() {
    CodeLab01Theme {
        MyApp()
    }
}