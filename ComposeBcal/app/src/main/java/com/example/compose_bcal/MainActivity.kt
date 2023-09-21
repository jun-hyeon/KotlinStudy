package com.example.compose_bcal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose_bcal.ui.theme.ComposeBcalTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeBcalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = viewModel<BmiViewModel>()
                    val navController = rememberNavController()

                    val bmi = viewModel.bmi.value

                    NavHost(navController = navController, startDestination = "Home"){
                        composable(route = "Home"){
                            HomeScreen(){ height, weight ->
                                 viewModel.bmiCalculate(height, weight)
                                 navController.navigate("result")
                            }
                        }
                        composable(route = "result"){
                            ResultScreen(navController = navController, bmi = bmi)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onResultClicked: (Double, Double) -> Unit,){
    val (height, setHeight) = rememberSaveable{
        mutableStateOf("")
    }

    val(weight, setWeight) = rememberSaveable{
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("비만도 계산기")}) 
        },

    ){

        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = height,
                onValueChange = setHeight,
                label = { Text(text = "키")},
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            OutlinedTextField(
                value = weight,
                onValueChange = setWeight,
                label = { Text(text = "몸무게")},
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
                )
            Button(
                onClick = {
                    if(height.isNotEmpty() && weight.isNotEmpty()){
                        onResultClicked(height.toDouble(), weight.toDouble())
                    }

                },
                modifier = Modifier
                    .align(Alignment.End)
                    .width(100.dp)
            ) {
                Text(text = "결과")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavController, bmi: Double){
    val text = when{
        bmi >= 35 -> "고도 비만"
        bmi >= 30 -> "2단계 비만"
        bmi >= 25 -> "1단계 비만"
        bmi >= 23 -> "과체중"
        bmi >= 18.5 -> "정상"
        else -> "저체중"
    }

    val imageRes = when{
        bmi >= 23 -> R.drawable.baseline_sentiment_very_dissatisfied_24
        bmi >= 18.5 -> R.drawable.baseline_sentiment_satisfied_24
        else -> R.drawable.baseline_sentiment_dissatisfied_24
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "비만도 계산기")},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "home",
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            }

                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text, fontSize = 30.sp)
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = imageRes),
                contentDescription =null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

class BmiViewModel : ViewModel(){
    private val _bmi = mutableStateOf(0.0)
    val bmi : State<Double> = _bmi

    fun bmiCalculate(height: Double, weight: Double){
        _bmi.value = weight/(height / 100.0).pow(2.0)
    }
}

