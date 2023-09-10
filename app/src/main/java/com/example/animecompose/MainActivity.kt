package com.example.animecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animecompose.pages.CollapsingToolbarExample
import com.example.animecompose.pages.DetailsPage
import com.example.animecompose.pages.HomePage
import com.example.animecompose.ui.theme.AnimeComposeTheme
import com.example.animecompose.viewmodel.CountViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeComposeTheme {

                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomePage(navController = navController)
                    }
                    composable("details"){
                        DetailsPage(navController = navController)
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    countViewModel: CountViewModel = viewModel()
) {

    val count by countViewModel.count.observeAsState()
    val mutableCount by countViewModel.mutableCount





    Column(
        modifier = Modifier.wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Count ${mutableCount}!")
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { countViewModel.increase() }) {
            Text(text = "Add")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    AnimeComposeTheme {
        Greeting("Android")
//        HomePage()
    }
}