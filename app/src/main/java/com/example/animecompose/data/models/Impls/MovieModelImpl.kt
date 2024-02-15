package com.example.animecompose.data.models.Impls

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
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
import com.example.animecompose.state.UiState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
//        onResponse: (UiState?) -> Unit
    ): List<MovieVO> {

        return withContext(Dispatchers.IO) {
            val response = mTheMovieApi.getPopularMovies();
            if (response.isSuccessful) {
                response.body()?.results ?: throw IOException("Empty response")
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

    override suspend fun getMoviesGenres(): Flow<UiState> {

        val genres: Flow<UiState> = flow {

            emit(UiState.Loading)
            try {

                val response = mTheMovieApi.getMoviesGenres()
                if (response.isSuccessful) {
//                    emit(UiState.Success(response.body()?.genres))
                    response.body()?.genres.let {
                        saveMoviesGenres(it!!)
                        genreDao.getGenres().collect {
                            if (it.isNullOrEmpty()) {
                                emit(UiState.Loading)
                            }
                            emit(UiState.Success(data = it))
                        }
                    }
                } else {
                    emit(UiState.Error(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                emit(UiState.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)

        return genres
    }

    override suspend fun saveMoviesGenres(genres: List<GenresVO>) {
        genreDao.saveGenres(genres)
    }

    override suspend fun getPopularPeople(): Flow<UiState> {
        val people: Flow<UiState> = flow<UiState> {
            emit(UiState.Loading)

            try {

                val response = mTheMovieApi.getPopularPeople()
                if (response.isSuccessful) {
                    response.body()?.results.let {
                        savePopularPeopleToDb(it!!)
                        getPopularPeopleFromDb().collect {
                            print("reloaded")
                            emit(UiState.Success(data = it))
                        }
                    }
                } else {
                    emit(UiState.Error(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                emit(UiState.Error(e.localizedMessage))
            }

        }.flowOn(Dispatchers.IO)

        return people
    }

    override suspend fun savePopularPeopleToDb(actorList: List<ActorVO>) {
        actorDao.insertActors(actorList)
        print("saved")
    }

    override suspend fun getPopularPeopleFromDb(): Flow<List<ActorVO>> {
        return actorDao.loadAllActors()
    }

    override suspend fun getMoviesByGenres(genreId: String?): Flow<UiState> {
        val movies: Flow<UiState> = flow<UiState> {
            emit(UiState.Loading)

            try {
                val response = mTheMovieApi.getMoviesByGenres(genre = genreId ?: "28")
                if (response.isSuccessful) {
                    emit(UiState.Success(data = response.body()?.results))
                } else {
                    emit(UiState.Error(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                emit(UiState.Error(e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)

        return movies
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
    override suspend fun saveAllMovies(moives: List<MovieVO>) {

    }

    override suspend fun saveMovie(movie: MovieVO) {
        movieDao.saveMovie(movie)
        Log.i("movie", "saveMovie: saved")
    }

    override fun getMovieByIdFromDb(movieId: Int): Flow<MovieVO> {
        return movieDao.getMovieDetailsById(movieId)
    }

    override suspend fun saveMovieCreditToDb(movieCreditResponse: MovieCreditResponse) {
        movieCreditDao.saveMovieCredit(movieCreditResponse)
        println("saved")
    }

    override fun getMovieCreditFromDb(movieId: Int): Flow<MovieCreditResponse> {
        return movieCreditDao.getMovieCreditById(movieId)
    }
}