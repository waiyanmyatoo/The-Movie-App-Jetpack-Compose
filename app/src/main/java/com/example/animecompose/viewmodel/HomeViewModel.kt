package com.example.animecompose.viewmodel

import androidx.lifecycle.*
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

    private var _popularMoviesLiveData: MediatorLiveData<UiState> =
        MediatorLiveData(UiState.Loading)
    var popularMoviesLiveData: LiveData<UiState> = _popularMoviesLiveData

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


            _genres.addSource(mMovieModel.getMoviesGenres().asLiveData()) {
                _genres.postValue(it)
                if (it is UiState.Success) {
                    val data = it.data as List<GenresVO>?
                    getMoviesByGenres(data?.first()?.id?.toString())
                }
            }

            _actorList.addSource(mMovieModel.getPopularPeople().asLiveData()) {
                _actorList.postValue(it)
            }

        }
    }

    suspend fun getPopularMovies() {
        try {
            val pMovieList = mMovieModel.getPopularMovies()
            _popularMoviesLiveData.postValue(UiState.Success(data = pMovieList))
        } catch (error: Exception) {
            _popularMoviesLiveData.postValue(UiState.Error(error.localizedMessage.toString()))
        }

    }

    fun getMoviesByGenres(genreId: String?) {
        viewModelScope.launch {
            _movieByGenre.addSource(mMovieModel.getMoviesByGenres(genreId).asLiveData()) {
//                Log.i("Movie by genres", "getMoviesByGenres:")
                _movieByGenre.postValue(it);
            }
        }
    }
}