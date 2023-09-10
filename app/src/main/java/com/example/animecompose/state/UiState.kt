package com.example.animecompose.state

import com.example.animecompose.data.vos.MovieVO

sealed class UiState() {
    data class Success(val movieList: List<MovieVO>?): UiState()
    object Loading: UiState()
    data class Error(val error: String?): UiState()

}