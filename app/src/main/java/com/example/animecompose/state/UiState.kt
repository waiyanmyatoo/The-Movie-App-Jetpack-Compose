package com.example.animecompose.state

import com.example.animecompose.data.vos.MovieVO

sealed class UiState() {
    data class Success(val data: Any?) : UiState()
    object Loading: UiState()
    data class Error(val error: String?): UiState()

}