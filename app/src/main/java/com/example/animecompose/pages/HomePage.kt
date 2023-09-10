package com.example.animecompose.pages

import android.graphics.PorterDuff
import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.animecompose.R
import com.example.animecompose.adapters.BannerAdapter
import com.example.animecompose.components.BannerView
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.state.UiState
import com.example.animecompose.ui.theme.AnimeComposeTheme
import com.example.animecompose.utils.IMAGE_BASE_URL
import com.example.animecompose.viewHolders.HomeViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavController
) {

    val movieList by homeViewModel.nowPlayingMoviesLiveData.observeAsState()
    val popularMovieList by homeViewModel.popularMoviesLiveData.observeAsState()

    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Home", "About", "Settings")

    Log.i("Reload TAG", "HomePage: ")

    LaunchedEffect(Unit) {
        Log.i("Reload effect TAG", "HomePage: ")
//        homeViewModel.getInitialData()
    }


    Scaffold(modifier = modifier, backgroundColor = Color(0xff1f2532), topBar = {
        AppBarView()
    }) { contentPadding ->
        // Screen content
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CarouselWithDots(modifier = Modifier.height(250.dp), nowPlayingMovieList = movieList)
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalMovieSection(
                        sectionTitle = stringResource(id = R.string.lbl_best_popular_films_and_serials),
                        movieList = popularMovieList,
                        onClickMovie = {
                            navController.navigate("Details")
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    MoviesTimeSection()
                    Spacer(modifier = Modifier.height(20.dp))
                    ScrollableTabRow(selectedTabIndex = 0) {
                        tabs.forEachIndexed { index, title ->
                            Tab(text = { Text(title) },
                                selected = tabIndex == index,
                                onClick = { tabIndex = index }
                            )
                        }
                    }
                    HorizontalMovieSection(
                        sectionTitle = stringResource(id = R.string.lbl_best_popular_films_and_serials),
                        movieList = popularMovieList,
                        onClickMovie = {

                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                }

            }

        }


    }

}


@Composable
fun MoviesTimeSection(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(0xff1F2A33))
                .height(180.dp)
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.margin_medium_3
                        ),
                        bottom = dimensionResource(
                            id = R.dimen.margin_medium_3
                        ),
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(id = R.string.lbl_check_movie_showtimes),
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold, fontSize = 24.sp
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.lbl_see_more),
                        color = colorResource(id = R.color.colorAccent),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                        )
                    )
                }
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_location_on_24),
                    contentDescription = "Location Icon",
                    tint = Color.White,
                    modifier = Modifier.size(
                        width = dimensionResource(id = R.dimen.margin_xxlarge),
                        height = dimensionResource(
                            id = R.dimen.margin_xxlarge
                        )
                    )
                )
            }
        }
    }

}

@Composable
fun HorizontalMovieSection(
    modifier: Modifier = Modifier,
    sectionTitle: String, movieList: UiState?,
    onClickMovie: (MovieVO) -> Unit
) {
    Column() {
        Text(
            text = sectionTitle,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin_card_medium_2))
        )
        Spacer(modifier = Modifier.height(20.dp))
        when (movieList) {
            is UiState.Loading -> {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        color = colorResource(id = R.color.colorAccent)
                    )
                }
            }
            is UiState.Error -> Text(
                text = "Error happened", color = Color.White
            )
            is UiState.Success -> {
                HorizontalListSection(
                    movieList = (movieList as UiState.Success).movieList,
                    onClickMovie = onClickMovie
                )
            }
            else -> {
                Text(text = "Else ${movieList}", color = Color.White)
            }
        }
    }
}

@Composable
fun HorizontalListSection(
    modifier: Modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_card_medium_2)),
    movieList: List<MovieVO>?,
    onClickMovie: (MovieVO) -> Unit
) {

    LazyRow(content = {
        itemsIndexed(movieList ?: emptyList()) { index, item ->
            Box(
                modifier = Modifier
                    .width(128.dp)
                    .wrapContentWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.margin_card_medium_2),
                        end = if (index == movieList?.lastIndex) 12.dp else 0.dp
                    )
                    .clickable { onClickMovie(item) }
            ) {
                Column {
                    AsyncImage(
                        model = "$IMAGE_BASE_URL${item.posterPath}",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = item.title ?: "WestWorld",
                        modifier = Modifier.padding(6.dp),
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        modifier = Modifier.wrapContentSize(), verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "8.20",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        AndroidView(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 8.dp),
                            factory = { context ->
                                RatingBar(context, null, android.R.attr.ratingBarStyleSmall).apply {
                                    this.numStars = 5;
                                    this.rating = 5F;
                                    this.setIsIndicator(true);
                                    this.stepSize = 1F;
                                    val drawable = progressDrawable.mutate()
                                    drawable.setColorFilter(
                                        Color.Yellow.toArgb(), PorterDuff.Mode.SRC_IN
                                    )
                                    progressDrawable = drawable
                                }
                            },
                        )


                    }
                }
            }

        }
    })
}


@Composable
fun AppBarView(modifier: Modifier = Modifier) {
    TopAppBar(
//        modifier = Modifier.background(Color(R.color.colorPrimaryDark)),
        backgroundColor = Color(0xff161c24),
        contentColor = Color.White,
        title = {
            Text(
                text = "Discover", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )

            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
//                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )

            }
        },
    )
}

@Composable
fun CarouselWithDots(modifier: Modifier = Modifier, nowPlayingMovieList: List<MovieVO>?) {

    val mBannerAdapter: BannerAdapter = BannerAdapter(nowPlayingMovieList)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(R.dimen.height_banner.dp)
    ) {
        BannerView(mBannerAdapter = mBannerAdapter)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    AnimeComposeTheme {
//        HomePage()
    }
}