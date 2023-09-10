package com.example.animecompose.data.models.Impls

import android.annotation.SuppressLint
import com.example.animecompose.data.models.BaseModel
import com.example.animecompose.data.models.MovieModel
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.state.UiState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException

object MovieModelImpl : MovieModel, BaseModel() {

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
    ): Flow<UiState> {
        val popularMovies: Flow<UiState> = flow<UiState> {
            emit(UiState.Loading)
            try {
                val response = mTheMovieApi.getPopularMovies();
                if (response.isSuccessful) {
                    emit(UiState.Success(response.body()?.results))
                } else {
                    emit(UiState.Error(response.errorBody().toString()))
                }
            } catch (e: IOException) {
                emit(UiState.Error(e.localizedMessage))
            } catch (e: Exception) {
                emit(UiState.Error(e.localizedMessage))
            }

        }.flowOn(Dispatchers.IO)
//        mTheMovieApi.getPopularMovies(page = 1)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.d("Popular list", "getPopularMovies: ${it.results?.size}")
//                    onResponse(UiState.Success(it.results))
//                },
//                {
//                    onResponse(UiState.Error(it.localizedMessage))
//                }
//            )

        return popularMovies
    }
}