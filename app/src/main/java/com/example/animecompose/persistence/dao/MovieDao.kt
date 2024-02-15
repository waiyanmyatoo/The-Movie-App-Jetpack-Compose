package com.example.animecompose.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animecompose.data.vos.MovieVO
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllMovies(movies: List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieVO)

    @Query("select * from MovieVO where id == :movieId")
    fun getMovieDetailsById(movieId: Int): Flow<MovieVO>
}