package com.example.animecompose.data.models

import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.state.UiState
import kotlinx.coroutines.flow.Flow

interface MovieModel {

    fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>?) -> Unit, onFailure: (String) -> Unit
    )

    suspend fun getPopularMovies(
//        onResponse: (UiState?) -> Unit
    ): Flow<UiState>
}