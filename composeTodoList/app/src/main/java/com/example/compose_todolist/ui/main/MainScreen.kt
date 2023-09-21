package com.example.compose_todolist.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.compose.ColorTheme
import com.example.compose_todolist.R
import com.example.compose_todolist.ui.main.components.TodoItem
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
){
    val snackBarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }


    ColorTheme {

        Scaffold(
            snackbarHost = { SnackbarHost (hostState = snackBarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(text = "오늘 할 일")},
                    colors = TopAppBarDefaults
                        .smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,


                        )
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ){
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    value = text,
                    onValueChange = {field ->
                        text = field
                    },
                    placeholder = { Text(text = "할 일", color = Color.Gray)},
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = "plus"
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.addTodo(text)
                        text = ""
                        keyboardController?.hide()
                        Log.d("MainScreen",viewModel.items.toString())
                    })
                )
                Divider()

                LazyColumn(
                    contentPadding =  PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ){
                    items(viewModel.items.value){todoItem ->
                        Column {
                            TodoItem(todo = todoItem,
                                onClick = {todo ->
                                    viewModel.toggle(todo.uid)
                                },
                                onDeleteClick = {todo ->
                                    viewModel.delete(todo.uid)

                                    scope.launch {
                                        val result = snackBarHostState.showSnackbar(
                                            message = "할 일 삭제됨",
                                            actionLabel = "취소",
                                            duration = SnackbarDuration.Short
                                        )
                                        if(result == SnackbarResult.ActionPerformed){
                                            viewModel.restoreTodo()
                                        }
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(color = Color.Black, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }


}

