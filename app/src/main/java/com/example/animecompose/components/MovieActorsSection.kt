package com.example.animecompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.animecompose.R
import com.example.animecompose.data.vos.ActorVO
import com.example.animecompose.utils.IMAGE_BASE_URL
import com.example.animecompose.utils.colorR
import com.example.animecompose.utils.dimenR
import com.example.animecompose.utils.fSize

@Composable
fun MovieActorsListSection(
    peopleList: List<ActorVO>,
    title: String = stringResource(id = R.string.lbl_best_actors),
    modifier: Modifier = Modifier
) {

    Column(modifier = Modifier.wrapContentSize()) {
        TitleView(
            title = title,
            modifier = Modifier.padding(start = R.dimen.margin_card_medium_2.dimenR())
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(content = {
            itemsIndexed(items = peopleList ?: emptyList()) { index, actor ->

                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .wrapContentHeight()
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_card_medium_2),
                            end = if (index == peopleList?.lastIndex) 12.dp else 0.dp
                        )
                ) {
                    AsyncImage(
                        model = "$IMAGE_BASE_URL${actor.profilePath}",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp)
                    )
                    Box(
                        modifier = modifier
                            .width(150.dp)
                            .height(190.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        R.color.colorPrimary.colorR()
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .padding(
                                dimensionResource(id = R.dimen.margin_card_medium)
                            )
                            .wrapContentSize()
                            .align(Alignment.BottomStart),

                        ) {
                        Text(
                            text = actor?.name ?: "Sydney Sweeney",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = R.dimen.text_regular.fSize(),
                        )
                        Spacer(modifier = Modifier.height(R.dimen.margin_small.dimenR()))
                        Row {
                            Icon(
                                imageVector = Icons.Filled.ThumbUp,
                                contentDescription = null,
                                tint = R.color.colorAccent.colorR(),
                                modifier = Modifier.size(R.dimen.margin_medium_2.dimenR())
                            )
                            Spacer(modifier = Modifier.width(R.dimen.margin_small.dimenR()))
                            Text(
                                text = "You liked 15 movies",
                                color = R.color.colorSecondaryText.colorR(),
                                fontWeight = FontWeight.Bold,
                                fontSize = R.dimen.text_small.fSize(),
                            )
                        }
                    }
                }


            }
        })
    }


}