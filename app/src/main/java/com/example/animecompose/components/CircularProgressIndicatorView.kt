package com.example.animecompose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.animecompose.R

@Composable
fun CircularProgressIndicatorView() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center),
            color = colorResource(id = R.color.colorAccent)
        )
    }
}