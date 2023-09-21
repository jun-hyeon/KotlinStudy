package com.example.comopse_webview

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comopse_webview.ui.theme.ComopseWebViewTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainViewModel>()

            ComopseWebViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel : MainViewModel){
    val focusManager = LocalFocusManager.current
    val (inputUrl, setUrl) = rememberSaveable{
        mutableStateOf("https://www.google.com")
    }
    val snackBarHostState = remember{
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
                       SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = colorResource(id = R.color.white)
                ),
                    title = { Text(text = "나만의 웹 브라우저") },
                    actions = {
                        IconButton(onClick = {
                            viewModel.undo()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back",
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = {
                            viewModel.redo()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "forward",
                                tint = Color.White
                            )
                        }
                    }
                )
        },

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = inputUrl,
                onValueChange = setUrl,
                label = { Text(text = "https://")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.url.value = inputUrl
                    focusManager.clearFocus()
                })
            )
            Spacer(modifier = Modifier.height(16.dp))

            MyWebView(viewModel, snackBarHostState)
        }
    }
}

@Composable
fun MyWebView(
    viewModel : MainViewModel,
    snackBarHostState: SnackbarHostState
){

    val webView = rememberWebView()

    LaunchedEffect(Unit){
        viewModel.undoSharedFlow.collectLatest {
            if(webView.canGoBack()){
                webView.goBack()
            }else{
                snackBarHostState.showSnackbar("더 이상 뒤로 갈 수 없음")
            }
        }
    }

    LaunchedEffect(Unit){
        viewModel.redoSharedFlow.collectLatest {
            if(webView.canGoForward()){
                webView.goForward()
            }else{
                snackBarHostState.showSnackbar("더 이상 앞으로 갈 수 없음")
            }
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            webView
    },
        update = {webView ->
            webView.loadUrl(viewModel.url.value)

        }
    )
}

@Composable
fun rememberWebView(): WebView{
    val context = LocalContext.current
    val webView = remember{
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("https://google.com")
        }
    }
    return webView
}