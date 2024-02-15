package com.example.animecompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Int.colorR(): Color {
    return colorResource(id = this)
}

@Composable
fun Int.dimenR(): Dp {
    return dimensionResource(this)
}

@Composable
fun Int.fSize(): TextUnit {
    return dimensionResource(this).value.sp
}

@Composable
fun Int.label(): String {
    return stringResource(this)
}