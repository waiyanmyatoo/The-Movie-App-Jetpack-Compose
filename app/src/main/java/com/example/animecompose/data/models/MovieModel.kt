package com.example.animecompose.data.models

import androidx.lifecycle.LiveData
import com.example.animecompose.data.vos.ActorVO
import com.example.animecompose.data.vos.GenresVO
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.network.responses.MovieCreditResponse
import com.example.animecompose.state.UiState
import kotlinx.coroutines.flow.Flow

interface MovieModel {

    fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>?) -> Unit, onFailure: (String) -> Unit
    )

    suspend fun getPopularMovies(
//        onResponse: (UiState?) -> Unit
    ): List<MovieVO>

    suspend fun getMovieDetails(movieId: String): MovieVO

    suspend fun getMoviesGenres(): Flow<UiState>

    suspend fun saveMoviesGenres(genres: List<GenresVO>): Unit

    suspend fun getPopularPeople(): Flow<UiState>
    suspend fun savePopularPeopleToDb(actorList: List<ActorVO>): Unit

    suspend fun getPopularPeopleFromDb(): Flow<List<ActorVO>>

    suspend fun getMoviesByGenres(genreId: String?): Flow<UiState>

    suspend fun getMovieCredits(movieId: String): MovieCreditResponse

    // Db actions
    suspend fun saveAllMovies(moives: List<MovieVO>)

    suspend fun saveMovie(moive: MovieVO)

    fun getMovieByIdFromDb(movieId: Int): Flow<MovieVO>

    suspend fun saveMovieCreditToDb(movieCreditResponse: MovieCreditResponse)

    fun getMovieCreditFromDb(movieId: Int): Flow<MovieCreditResponse>
}