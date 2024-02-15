package com.example.animecompose

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.animecompose.components.MovieActorsListSection
import com.example.animecompose.components.StateView
import com.example.animecompose.components.TitleView
import com.example.animecompose.data.vos.GenresVO
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.databinding.ActivityMovieDetailsBinding
import com.example.animecompose.network.responses.MovieCreditResponse
import com.example.animecompose.state.UiState
import com.example.animecompose.ui.theme.AnimeComposeTheme
import com.example.animecompose.utils.BANNER_IMAGE_BASE_URL
import com.example.animecompose.utils.colorR
import com.example.animecompose.utils.dimenR
import com.example.animecompose.utils.fSize
import com.example.animecompose.utils.label
import com.example.animecompose.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    private lateinit var mDetailsViewModel: MovieDetailsViewModel


    companion object {

        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"                   //1

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)                          //2
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        var movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)

        movieId?.let {
            setUpViewModel(it)
        }
        observeLiveData()
        observeMovieCast()
        onClickListener()
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            }
//        }
    }

    private fun observeMovieCast() {
        mDetailsViewModel.movieCasts.observe(this) {
            it?.let {
                binding.cpActors.setContent {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(R.color.colorPrimary.colorR())
                            .padding(vertical = R.dimen.margin_card_medium_2.dimenR())
                    ) {
                        StateView(
                            state = it,
                            component = MovieActorsListSection(
                                peopleList = if (it is UiState.Success) ((it.data as MovieCreditResponse).cast
                                    ?: emptyList()) else emptyList(),
                                stringResource(id = R.string.lbl_actors)
                            )
                        )

                    }
                }
                binding.cpCreator.setContent {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(R.color.colorPrimary.colorR())
                            .padding(vertical = R.dimen.margin_card_medium_2.dimenR())
                    ) {
                        StateView(
                            state = it,
                            component = MovieActorsListSection(
                                peopleList = if (it is UiState.Success) ((it.data as MovieCreditResponse).crew
                                    ?: emptyList()) else emptyList(),
                                stringResource(id = R.string.lbl_creators)
                            )
                        )
                    }


                }
            }

        }


    }

    private fun onClickListener() {
        binding.btnBack.setOnClickListener {
            this.finish()
        }
    }

    private fun setUpViewModel(id: Int) {
        mDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        mDetailsViewModel.getMovieDetails(id)
        mDetailsViewModel.getMovieCredit(id)

    }


    private fun observeLiveData() {

        mDetailsViewModel.movieDetails?.observe(this) {
            it?.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        binding.cpvDetails.visibility = View.GONE
                        binding.tvDetailMovieName.text = "Loading"
                    }

                    is UiState.Error -> {
                        binding.tvDetailMovieName.text = "Something went wrong!"
                        Log.d("Error details", "observeLiveData: ${uiState.error}")
                    }

                    is UiState.Success -> {

                        bindData(uiState.data as MovieVO)

                    }

                    else -> {
                        Log.d("detailsError", "observeLiveData: ")
                    }
                }
            }
        }


    }

    private fun bindData(movie: MovieVO) {
        Glide.with(this).load("$BANNER_IMAGE_BASE_URL${movie.posterPath}")
            .into(binding.ivMovieDetail)
        binding.cpvDetails.visibility = View.VISIBLE
        binding.tvAppbarDetailMovieName.title = movie.title ?: ""
        binding.tvDetailMovieName.text = movie.title ?: ""
        binding.tvDetailMovieReleaseYear.text = movie.releaseDate?.substring(0, 4) ?: ""
        binding.tvNumberOfVotes.text = movie.voteCount.toString()
        binding.tvRating.text = movie.voteAverage.toString()
        movie.voteCount?.let {
            binding.tvNumberOfVotes.text = "$it VOTES"
        }
        binding.rbDetailMovieRating.rating = movie.getRatingBasedOnFiveStars()
        binding.rbDetailMovieRating.progressDrawable.setColorFilter(
            android.graphics.Color.YELLOW,
            PorterDuff.Mode.SRC_ATOP
        )
        setUpViews(movie)
    }

    private fun setUpViews(movie: MovieVO) {
        binding.cpvDetails.let {

            it.setContent {

                AnimeComposeTheme() {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        content = {
                            MovieDetailsGenreView(movie.genres)
                            MovieDetailsStoryLineSection(movie.overview)
                        })

                }

            }
        }
        binding.cpFilmInfo.let {
            it.setContent {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(R.dimen.margin_card_medium_2.dimenR())
                ) {
                    TitleView(
                        title = stringResource(id = R.string.LBL_about_films),
                        modifier = Modifier
                            .padding(vertical = R.dimen.margin_medium_2.dimenR())

                    )
                    MovieInfoView(
                        label = R.string.lbl_original_title.label(),
                        value = movie.originalTitle
                    )
                    MovieInfoView(
                        label = R.string.lbl_type.label(),
                        value = movie.genres?.map { g -> g.name }?.joinToString(", ")
                    )
                    MovieInfoView(
                        label = R.string.lbl_production.label(),
                        value = movie.productionCountries?.map { c -> c.name }?.joinToString(", ")
                    )
                    MovieInfoView(label = R.string.lbl_premiere.label(), value = movie.releaseDate)
                    MovieInfoView(label = R.string.lbl_description.label(), value = movie.overview)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}

@Composable
fun MovieInfoView(label: String, value: String?) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = R.dimen.margin_card_medium_2.dimenR())
    ) {
        Text(
            text = label,
            color = R.color.gray_400.colorR(),
            fontSize = R.dimen.text_regular_2x.fSize(),
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value ?: "---",
            color = Color.White,
            maxLines = 4,
            fontSize = R.dimen.text_regular_2x.fSize(),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsGenreView(genres: List<GenresVO>?, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_access_time_24),
                contentDescription = "Timer Icon",
                tint = colorResource(id = R.color.colorAccent)
            )
            Box(
                modifier = Modifier
                    .width(R.dimen.width_showcases.dimenR())
                    .wrapContentHeight()
            ) {
                FlowRow() {

                    genres?.map {
                        Text(
                            text = it.name ?: "--",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(4.dp)

                                .background(
                                    colorResource(id = R.color.colorPrimaryLight),
                                    CircleShape
                                )
                                .padding(horizontal = 7.dp, vertical = 6.dp)

                        )
                    }
                }

            }

        }
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
    }
}

@Composable
fun MovieDetailsStoryLineSection(overview: String?, modifier: Modifier = Modifier) {
    var spacer = dimensionResource(id = R.dimen.margin_medium_2)
    var colorAccent = colorResource(id = R.color.colorAccent)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(colorResource(id = R.color.colorPrimaryDark))
    ) {
        Column() {
            Spacer(modifier = Modifier.height(spacer))
            TitleView(title = stringResource(id = R.string.lbl_storyline))
            Spacer(modifier = Modifier.height(spacer))
            Text(
                text = overview ?: "No overview!",
                color = Color.White,
                fontSize = dimensionResource(id = R.dimen.text_regular).value.sp
            )
            Spacer(modifier = Modifier.height(spacer))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomButton(
                    contentColor = Color.White,
                    colorAccent,
                    FontWeight.Bold,
                    stringResource(id = R.string.lbl_play_trailer),
                )
                CustomButton(
                    contentColor = Color.White,
                    colorResource(id = R.color.colorPrimaryDark),
                    FontWeight.SemiBold,
                    stringResource(id = R.string.lbl_rate_movie),
                    borderColor = Color.White,
                    Icons.Filled.Star,
                    colorAccent
                )
            }
            Spacer(modifier = Modifier.height(spacer))
        }
    }
}

@Composable
fun CustomButton(

    contentColor: Color,
    backgroundColor: Color,
    fontWeight: FontWeight,
    text: String,
    borderColor: Color = Color.Transparent,
    icon: ImageVector = Icons.Default.PlayArrow,
    iconColor: Color = Color.White
) {
    Button(
        onClick = {
            Log.d("trailer", "MovieDetailsStoryLineSection: ")
        },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = Modifier
            .height(50.dp)
            .border(1.dp, color = borderColor, shape = CircleShape)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconColor)
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_small)))
        Text(text = text, fontWeight = fontWeight)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailsPreview() {
    AnimeComposeTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(R.dimen.margin_card_medium_2.dimenR())
        ) {
            TitleView(
                title = "About Film",
                modifier = Modifier.padding(vertical = R.dimen.margin_medium_2.dimenR())
            )
            MovieInfoView(
                label = R.string.lbl_original_title.label(),
                value = "How the Grinch Stole Chrismas"
            )
        }
    }
}

