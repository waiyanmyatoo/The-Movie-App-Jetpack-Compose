package com.example.animecompose.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.animecompose.data.vos.MovieVO
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAllMovies(movies: List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: MovieVO)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateMovie(movie: MovieVO)

    @Query("select * from MovieVO where id == :movieId")
     fun getMovieDetailsById(movieId: Int): Flow<MovieVO>

     @Query("select * from MovieVO where id == :movieId")
     suspend fun getMovieByIdOneTime(movieId: Int): MovieVO

    @Query("select * from MovieVO where type == :movieType order by insertionTimestamp desc")
     fun getMoviesByType(movieType: String): Flow<List<MovieVO>>

     @Query("select * from MovieVO order by releaseDate desc")
     fun getAllMovies(): Flow<List<MovieVO>>

    @Query("SELECT * FROM MovieVO WHERE genreIds LIKE '%' || :genresId || '%' order by insertionTimestamp desc")
     fun getMoviesByGenres(genresId: Int): Flow<List<MovieVO>>

    @Query("SELECT * FROM MovieVO WHERE genreIds LIKE '%' || :genreId || '%' order by insertionTimestamp asc limit 20")
    suspend fun getMoviesByGenresOneTime(genreId: Int): List<MovieVO>
}