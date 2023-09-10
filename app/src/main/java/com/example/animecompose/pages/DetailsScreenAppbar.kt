package com.example.animecompose.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CollapsingToolbarExample(navController: NavController) {
    var isToolbarExpanded by remember { mutableStateOf(true) }
    val toolbarHeight = with(LocalDensity.current) { (56.dp + 56.dp).toPx().roundToInt() }
    val scrollState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(50) {
                // Your list items here
                Text(text = "Item $it", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
            }
        }

        CollapsingToolbar(
            toolbarHeight = toolbarHeight,
            isToolbarExpanded = isToolbarExpanded
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                BasicTextField(
                    value = "",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),

                    textStyle = TextStyle(color = Color.White),
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CollapsingToolbar(
    toolbarHeight: Int,
    isToolbarExpanded: Boolean,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    val scrollPercent = if (isToolbarExpanded) {
        scrollState.value / toolbarHeight.toFloat()
    } else {
        1f
    }

    Box {
        Column {
            Spacer(modifier = Modifier.height(toolbarHeight.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationY = (-toolbarHeight * scrollPercent).dp.toPx()
                    }
            ) {
                content()
            }
        }

        Toolbar(isExpanded = isToolbarExpanded)
    }
}

@Composable
fun Toolbar(isExpanded: Boolean) {
    // Customize your toolbar here
    val backgroundColor = if (isExpanded) Color.Transparent else Color.Blue
    val titleColor = if (isExpanded) Color.Transparent else Color.White

    TopAppBar(
        backgroundColor = backgroundColor,
        elevation = 0.dp,
        modifier = Modifier.height(56.dp),
//        contentPadding = rememberInsetsPaddingValues(
//            insets = LocalWindowInsets.current.systemBars,
//            applyStart = false,
//            applyEnd = false,
//            applyTop = true,
//            applyBottom = false
//        )
    ) {
        Text(
            text = "Collapsing Toolbar",
            color = titleColor,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
