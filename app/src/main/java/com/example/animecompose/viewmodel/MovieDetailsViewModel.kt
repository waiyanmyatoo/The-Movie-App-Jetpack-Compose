package com.example.animecompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.animecompose.data.models.MovieModel
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.network.responses.MovieCreditResponse
import com.example.animecompose.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val _mMovieModel: MovieModel,
) :
    ViewModel() {

//    private var _mMovieModel: MovieModel = MovieModelImpl

    private var _movieDetails = MediatorLiveData<UiState>(UiState.Loading)
    var movieDetails: LiveData<UiState> = _movieDetails

    private var _movieCasts = MediatorLiveData<UiState>(UiState.Loading)
    var movieCasts: LiveData<UiState> = _movieCasts


    fun getMovieDetails(movieId: Int) {


        viewModelScope.launch(Dispatchers.IO) {
//            _movieDetails.addSource(_mMovieModel.getMovieByIdFromDb(movieId).asLiveData()) {
//                getMovieFromDB(it)
//            }

            _mMovieModel.getMovieDetails(movieId.toString()).let {
                runCatching {
                    it?.let { id ->
                        val movieFromDbToSync = _mMovieModel.getMovieByIdOneTime(movieId)
                        if (it == movieFromDbToSync) {
                            Log.i(
                                "TAGEqu",
                                "getMovieDetails: True ${it.hashCode()} ${movieFromDbToSync.hashCode()} \n ${it.id} ${movieFromDbToSync.id}"
                            )
                        } else {
                            Log.i(
                                "TAGEqu",
                                "getMovieDetails: False ${it.hashCode()} ${movieFromDbToSync.hashCode()}  \n" +
                                        " ${it.id} ${movieFromDbToSync.id}"
                            )
                            it.genreIds = movieFromDbToSync.genreIds
                            it.type = movieFromDbToSync.type
                            it.insertionTimestamp = movieFromDbToSync.insertionTimestamp
                            _movieDetails.postValue(UiState.Success(it))
                            _mMovieModel.saveMovie(it)

                        }

                    }
                }.onFailure {
                    _movieDetails.postValue(UiState.Error(it.localizedMessage))
                }
            }


        }
    }

    fun getMovieCredit(movieId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _movieCasts.addSource(_mMovieModel.getMovieCreditFromDb(movieId).asLiveData()) {
                    getMovieCreditFromDb(it)
                }
//                _mMovieModel.getMovieCreditFromDb(movieId).collect {
//                    getMovieCreditFromDb(it)
//                }

                _mMovieModel.getMovieCredits(movieId.toString()).let {
                    _mMovieModel.saveMovieCreditToDb(it)
                }


            }

        } catch (e: Exception) {
            _movieCasts.postValue(UiState.Error(e.localizedMessage))
        }
    }

    private fun getMovieCreditFromDb(movieCredit: MovieCreditResponse?) {
        if (movieCredit != null) {
            _movieCasts.postValue(UiState.Success(movieCredit))
        } else {
            _movieCasts.postValue(UiState.Loading)
        }
    }

    fun getMovieFromDB(movie: MovieVO?) {
        if (movie != null) {
            _movieDetails.postValue(UiState.Success(movie))
        } else {
            _movieDetails.postValue(UiState.Loading)
        }
    }


}