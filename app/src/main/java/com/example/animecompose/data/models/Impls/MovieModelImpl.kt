package com.example.animecompose.data.models.Impls

import android.annotation.SuppressLint
import android.util.Log
import com.example.animecompose.data.models.BaseModel
import com.example.animecompose.data.models.MovieModel
import com.example.animecompose.data.vos.ActorVO
import com.example.animecompose.data.vos.GenresVO
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.network.responses.MovieCreditResponse
import com.example.animecompose.persistence.dao.ActorDao
import com.example.animecompose.persistence.dao.GenreDao
import com.example.animecompose.persistence.dao.MovieCreditDao
import com.example.animecompose.persistence.dao.MovieDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

class MovieModelImpl @Inject constructor(
    private val actorDao: ActorDao,
    private val genreDao: GenreDao,
    private val movieDao: MovieDao,
    private val movieCreditDao: MovieCreditDao,
) : MovieModel, BaseModel() {

    @SuppressLint("CheckResult")
    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi.getNowPlayingMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results)
            },
                {
                    onFailure(it.localizedMessage ?: "")

                })
    }

    @SuppressLint("CheckResult")
    override suspend fun getPopularMovies(
    ): List<MovieVO> {

        return withContext(Dispatchers.IO) {
            val response = mTheMovieApi.getPopularMovies();
            if (response.isSuccessful) {
                response.body()?.results?.map { mv -> mv.copy(type = "popular") }
                    ?: throw IOException("Empty response")
            } else {
                throw IOException(
                    "Failed to fetch popular movies: ${
                        response.errorBody()?.string()
                    }"
                )
            }
        }


    }

    override suspend fun getMovieDetails(movieId: String): MovieVO {
        val movieDetails: MovieVO = withContext(Dispatchers.IO) {


            try {
                val response = mTheMovieApi.getMovieDetails(movieId = movieId)
                if (response.isSuccessful) {
                    response.body() ?: throw IOException("Failed to fetch movie deatils")
                } else {
                    throw IOException(
                        "Failed to fetch movie deatils ${
                            response.errorBody().toString()
                        }"
                    )

                }
            } catch (e: IOException) {
                throw IOException("Failed to fetch movie deatils ${e.localizedMessage}")
            } catch (e: Exception) {
                throw Exception("Failed to fetch movie deatils ${e.localizedMessage}")

            }

        }

//        movieDetails?.let {
//            saveMovie(it)
//        }

        return movieDetails
    }

    override suspend fun getMoviesGenres(): List<GenresVO> {

        val genres = runBlocking(Dispatchers.IO) {

            try {

                val response = mTheMovieApi.getMoviesGenres()
                if (response.isSuccessful) {
//                    emit(UiState.Success(response.body()?.genres))
                    response.body()?.genres ?: throw Exception("Error: There is not genres!")
                } else {
                    throw Exception("Failed to fetch genres: ${response.errorBody().toString()}")
                }
            } catch (e: Exception) {
                throw e
            }
        }

        genres?.let {
            saveMoviesGenres(it)
        }

        return genres
    }

    override suspend fun saveMoviesGenres(genres: List<GenresVO>) {
        genreDao.saveGenres(genres)
    }

    override fun getAllGenresFromDb(): List<GenresVO> {
        return runBlocking(Dispatchers.IO) {
            genreDao.getGenresOneTime()
        }
    }

    override suspend fun getPopularPeople(): List<ActorVO> {
        val people = runBlocking(Dispatchers.IO) {

            try {
                val response = mTheMovieApi.getPopularPeople()
                if (response.isSuccessful) {
                    response.body()?.results ?: throw Exception("Error: There is no data!")
                } else {
                    throw Exception(response.errorBody().toString())
                }
            } catch (e: Exception) {
                throw e
            }
        }
        return people
    }

    override suspend fun savePopularPeopleToDb(actorList: List<ActorVO>) {
        actorDao.insertActors(actorList)
        print("saved")
    }

    override suspend fun getPopularPeopleFromDb(): Flow<List<ActorVO>> {
        return actorDao.loadAllActors()
    }

    override suspend fun getMoviesByGenres(genreId: String?): List<MovieVO> {
        val movies = runBlocking(Dispatchers.IO) {


            try {
                val response = mTheMovieApi.getMoviesByGenres(genre = genreId ?: "28")
                if (response.isSuccessful) {
                    response.body()?.results ?: throw Exception("Faile to fetch movies")
                } else {
                    throw Exception(response.errorBody().toString())
                }
            } catch (e: Exception) {
                throw e
            }
        }

        return movies
    }

    override fun getMoviesByGenresFromDB(genreId: Int): List<MovieVO> {
        return runBlocking(Dispatchers.IO) { movieDao.getMoviesByGenresOneTime(genreId) }
    }

    override suspend fun getMovieCredits(movieId: String): MovieCreditResponse {
        val casts: MovieCreditResponse = withContext(Dispatchers.IO) {

            try {
                val response = mTheMovieApi.getMovieCredits(movieId)
                if (response.isSuccessful) {
                    response.body() ?: throw Exception("Failed to fetch movie credits")

                } else {
                    throw IOException(
                        "Failed to fetch movie deatils ${
                            response.errorBody().toString()
                        }"
                    )

                }
            } catch (e: Exception) {
                throw IOException(
                    "Failed to fetch movie deatils ${
                        e.localizedMessage
                    }"
                )

            }
        }

        return casts;
    }

    // Db actions
    override suspend fun saveAllMovies(movies: List<MovieVO>) {
        movieDao.saveAllMovies(movies)
        Log.i("movies", "saveAllMovies: ")
    }

    override suspend fun saveMovie(movie: MovieVO) {
        movieDao.saveMovie(movie)
        Log.i("movie", "saveMovie: saved")
    }

    override suspend fun updateMovie(movie: MovieVO) {
        movieDao.updateMovie(movie)
    }

    override fun getMovieByIdFromDb(movieId: Int): Flow<MovieVO> {
        return movieDao.getMovieDetailsById(movieId)
    }

    override suspend fun getMovieByIdOneTime(movieId: Int): MovieVO {
        return movieDao.getMovieByIdOneTime(movieId)
    }

    override suspend fun saveMovieCreditToDb(movieCreditResponse: MovieCreditResponse) {
        movieCreditDao.saveMovieCredit(movieCreditResponse)
        println("saved")
    }

    override fun getMovieCreditFromDb(movieId: Int): Flow<MovieCreditResponse> {
        return movieCreditDao.getMovieCreditById(movieId)
    }

    override fun getMoviesByType(movieType: String?): Flow<List<MovieVO>> {
        return movieDao.getMoviesByType(movieType ?: "popular")
//       return movieDao.getAllMovies()
    }
}