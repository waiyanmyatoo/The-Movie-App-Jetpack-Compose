package com.example.animecompose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.animecompose.data.models.MovieModel
import com.example.animecompose.data.vos.GenresVO
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mMovieModel: MovieModel
) : ViewModel() {


    private var _nowPlayingMoviesLiveData: MutableLiveData<List<MovieVO>> = MutableLiveData(
        emptyList()
    )
    var nowPlayingMoviesLiveData: LiveData<List<MovieVO>> = _nowPlayingMoviesLiveData

    private var _popularMoviesLiveData = MediatorLiveData<UiState>(UiState.Loading)
    val popularMoviesLiveData = _popularMoviesLiveData

    private var _genres: MediatorLiveData<UiState> = MediatorLiveData(UiState.Loading)
    var genres: LiveData<UiState> = _genres

    private var _actorList: MediatorLiveData<UiState> = MediatorLiveData(UiState.Loading)
    var actorList: LiveData<UiState> = _actorList

    private var _movieByGenre: MediatorLiveData<UiState> = MediatorLiveData(UiState.Loading)
    var moviesByGenre: LiveData<UiState> = _movieByGenre


    init {
        getInitialData()

    }


    fun getInitialData() {


        viewModelScope.launch {
            mMovieModel.getNowPlayingMovies({
                _nowPlayingMoviesLiveData.postValue(it)
            }, {
//                Log.d("Error ", "getInitialData: $it")
            })


            getPopularMovies()

            getMovieGenres()

            getPopularPeople()


        }
    }

    private suspend fun getPopularPeople() {
        try {
            mMovieModel.getPopularPeople()?.let {
                mMovieModel.savePopularPeopleToDb(it)
            }

            _actorList.addSource(mMovieModel.getPopularPeopleFromDb().asLiveData()) {
                it?.let {
                    _actorList.postValue(UiState.Success(it))
                }
            }
        } catch (e: Exception) {
            _actorList.postValue(UiState.Error(e.localizedMessage))
        }

    }

    suspend fun getPopularMovies() {
        try {
            _popularMoviesLiveData.addSource(mMovieModel.getMoviesByType("popular").asLiveData()) {
                _popularMoviesLiveData.postValue(UiState.Success(it))
            }
            val pMovieList = mMovieModel.getPopularMovies().map { movieVO ->
                movieVO.apply {
                    insertionTimestamp  = System.nanoTime()
                }
            }
            mMovieModel.saveAllMovies(pMovieList)
        } catch (error: Exception) {
            _popularMoviesLiveData.postValue(UiState.Error(error.localizedMessage.toString()))
        }
    }

    suspend fun getMovieGenres() {
        try {
            getAllGenresFromDb()
//            _genres.addSource(mMovieModel.getAllGenresFromDb().asLiveData()) {
//                _genres.postValue(UiState.Success(it))
//            }
            mMovieModel.getMoviesGenres()?.let {
                it?.let {
                    val data = it as List<GenresVO>?
                    getAllGenresFromDb()
                    getMoviesByGenres(data?.first()?.id ?: 0)
                }
            }
        } catch (e: Exception) {
            _genres.postValue(UiState.Error(e.localizedMessage))
        }
    }

    fun getMoviesByGenres(genreId: Int?) {
        try {
            viewModelScope.launch {
                getMoviesByGenresFromDB(genreId)
                mMovieModel.getMoviesByGenres(genreId?.toString() ?: "28")?.let {
//                    _movieByGenre.postValue(UiState.Success(it));
                    if(!it.isNullOrEmpty()){
                        it?.forEach {
                           val movieFromDb = mMovieModel.getMovieByIdOneTime(it.id ?: 0)
                            Log.i("TAGMovie", "getMoviesByGenres: ${it.title}")
                            if (movieFromDb == null) mMovieModel.saveMovie(it.apply { insertionTimestamp = System.nanoTime() }) else null
                        }
                    }
                    getMoviesByGenresFromDB(genreId)
                }
            }
        } catch (e: Exception) {
            _movieByGenre.postValue(UiState.Error(e.localizedMessage))
        }

    }

    fun getAllGenresFromDb() {
        mMovieModel.getAllGenresFromDb()?.let {
            _genres.postValue(UiState.Success(it))

        }
    }


    fun getMoviesByGenresFromDB(genreId: Int?){
        mMovieModel.getMoviesByGenresFromDB(genreId ?: 28)?.let {
            if(!it.isNullOrEmpty()) {
                _movieByGenre.postValue(UiState.Success(it));
            }
        }
    }
}