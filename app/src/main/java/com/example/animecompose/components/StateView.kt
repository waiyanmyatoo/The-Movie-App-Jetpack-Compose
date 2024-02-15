package com.example.animecompose.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.animecompose.state.UiState

@Composable
fun StateView(state: UiState, component: Unit, ) {
    when (state) {
        is UiState.Loading -> CircularProgressIndicatorView()
        is UiState.Error -> {
            Text(
                text = "${(state as UiState.Error).error}",
                color = Color.White
            )
        }
        is UiState.Success -> {
            component
        }
        else -> CircularProgressIndicatorView()
    }
}