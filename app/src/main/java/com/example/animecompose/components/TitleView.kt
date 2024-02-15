package com.example.animecompose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.animecompose.R
import com.example.animecompose.utils.fSize

@Composable
fun TitleView(title: String, modifier: Modifier = Modifier){
    Text(
        text = title,
        color = colorResource(id = R.color.colorSecondaryText),
        fontWeight = FontWeight.Bold,
        fontSize = R.dimen.text_regular.fSize(),
        modifier = modifier
    )
}