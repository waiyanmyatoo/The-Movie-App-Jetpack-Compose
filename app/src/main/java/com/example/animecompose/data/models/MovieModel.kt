package com.example.animecompose.data.models

import com.example.animecompose.data.vos.ActorVO
import com.example.animecompose.data.vos.GenresVO
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.network.responses.MovieCreditResponse
import kotlinx.coroutines.flow.Flow

interface MovieModel {

    fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>?) -> Unit, onFailure: (String) -> Unit
    )

    suspend fun getPopularMovies(
//        onResponse: (UiState?) -> Unit
    ): List<MovieVO>

    suspend fun getMovieDetails(movieId: String): MovieVO

    suspend fun getMoviesGenres(): List<GenresVO>


    suspend fun getPopularPeople(): List<ActorVO>
    suspend fun savePopularPeopleToDb(actorList: List<ActorVO>): Unit

    suspend fun getPopularPeopleFromDb(): Flow<List<ActorVO>>

    suspend fun getMoviesByGenres(genreId: String?): List<MovieVO>

    suspend fun getMovieCredits(movieId: String): MovieCreditResponse

    // Db actions

    suspend fun saveMoviesGenres(genres: List<GenresVO>): Unit

    fun getMoviesByGenresFromDB(genreId: Int): List<MovieVO>

    fun getAllGenresFromDb(): List<GenresVO>

    suspend fun saveAllMovies(moives: List<MovieVO>)

    suspend fun saveMovie(moive: MovieVO)

    suspend fun updateMovie(movie: MovieVO)

    fun getMovieByIdFromDb(movieId: Int): Flow<MovieVO>

    suspend fun getMovieByIdOneTime(movieId: Int): MovieVO

    suspend fun saveMovieCreditToDb(movieCreditResponse: MovieCreditResponse)

    fun getMovieCreditFromDb(movieId: Int): Flow<MovieCreditResponse>

    fun getMoviesByType(movieType: String?) : Flow<List<MovieVO>>
}