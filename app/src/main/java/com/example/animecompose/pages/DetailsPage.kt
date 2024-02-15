package com.example.animecompose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsPage(navController: NavController) {

    val scrollState = rememberCollapsingToolbarScaffoldState()


    val numList = (1..50).toList()


    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Box(
                modifier = Modifier
                    .let {
                        if (!scrollState.toolbarState.canScrollBackward) {
                            it.background(Color.Red)
                        }
                        it.background(Color.LightGray)
                    }
                    .fillMaxWidth()
                    .height(150.dp)
//                    .road(whenCollapsed = Alignment.CenterStart, whenExpanded = Alignment.Center)
                    .pin()
            ) {
//                Image(
//                    modifier = Modifier
//                        .pin()
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    painter = painterResource(id = R.drawable.placeholder_wolverine_image),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = null
//                )
            }

            Text(
                text = "Title",
                modifier = Modifier
                    .road(Alignment.CenterStart, Alignment.BottomEnd)
                    .padding(60.dp, 16.dp, 16.dp, 16.dp),
                color = Color.White,
                fontSize = 18.sp
            )


        },

        ) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(numList.size) { item ->
                Text(modifier = Modifier.padding(8.dp), text = "Item $item")
            }
        }
    }


}