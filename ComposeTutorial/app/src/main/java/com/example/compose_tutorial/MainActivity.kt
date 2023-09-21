package com.example.compose_tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_tutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeTutorialTheme {
                Surface {

                    ContentView()
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentView(){
        Scaffold(

            topBar = { MyAppBar() },
        ) {
            Column (
                modifier = Modifier.padding(it)
            ){
                RandomUserListView(randomUsers = DummyDataProvider.userList)
            }
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(){
   TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            titleContentColor = Color.Black,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
          Text(
               modifier = Modifier
                   .background(color = MaterialTheme.colorScheme.tertiary),
                text = stringResource(id = R.string.app_name),
                fontSize = 18.sp,
          )
       },
    )
}

@Composable
fun RandomUserListView(randomUsers: List<RandomUser>){
    LazyColumn{
        items(randomUsers){
            RandomUserView(randomUser = it)
        }
    }
}

@Composable
fun RandomUserView(randomUser: RandomUser){

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row (
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Box(modifier = Modifier
                .size(width = 60.dp, height = 60.dp)
                .clip(CircleShape)
                .background(Color.Red)
            )

            Column {
                Text(
                    text = randomUser.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = randomUser.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}








