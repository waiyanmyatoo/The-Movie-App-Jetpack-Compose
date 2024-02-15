package com.example.animecompose.network

import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.network.responses.GenresResponse
import com.example.animecompose.network.responses.MovieCreditResponse
import com.example.animecompose.network.responses.MovieListResponse
import com.example.animecompose.network.responses.PeopleResponse
import com.example.animecompose.utils.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieApi {

    @GET(API_GET_NOW_PLAYING)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) apikey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Observable<MovieListResponse>

    @GET(API_GET_POPULAR_MOVIES)
    suspend fun getPopularMovies(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Response<MovieListResponse>

    @GET("$API_GET_MOVIE_DETAILS/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Response<MovieVO>

    @GET("$API_GET_MOVIE_GENRES")
    suspend fun getMoviesGenres(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY
    ): Response<GenresResponse>

    @GET("$API_GET_POPULAR_PEOPLE")
    suspend fun getPopularPeople(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1
    ): Response<PeopleResponse>

    @GET("$API_GET_DISCOVER_MOVIES")
    suspend fun getMoviesByGenres(
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
        @Query(PARAM_GENRES) genre: String
    ): Response<MovieListResponse>

    @GET("$API_GET_MOVIE_DETAILS/{movie_id}$API_GET_MOVIE_CREDITS")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: String,
        @Query(PARAM_API_KEY) apiKey: String = MOVIE_API_KEY,
    ): Response<MovieCreditResponse>
}