package com.example.animecompose.viewHolders

import android.util.Log
import androidx.lifecycle.*
import com.example.animecompose.data.models.Impls.MovieModelImpl
import com.example.animecompose.data.models.MovieModel
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.state.UiState
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    var liveDataMerger = MediatorLiveData<UiState>()

    private var mMovieModel: MovieModel = MovieModelImpl

    private var _nowPlayingMoviesLiveData: MutableLiveData<List<MovieVO>> = MutableLiveData(
        emptyList()
    )
    var nowPlayingMoviesLiveData: LiveData<List<MovieVO>> = _nowPlayingMoviesLiveData

    private var _popularMoviesLiveData: MediatorLiveData<UiState> =
        MediatorLiveData(UiState.Loading)
    var popularMoviesLiveData: LiveData<UiState> = _popularMoviesLiveData

    init {
        getInitialData()
    }


    fun getInitialData() {
        viewModelScope.launch {
            mMovieModel.getNowPlayingMovies({
                _nowPlayingMoviesLiveData.postValue(it)
            }, {
                Log.d("Error ", "getInitialData: $it")
            })



            _popularMoviesLiveData.addSource(mMovieModel.getPopularMovies().asLiveData(), {
                Log.d("UiState", "getInitialData: ${it.toString()}")
                _popularMoviesLiveData.postValue(it)
            })


        }
    }
}