package com.example.animecompose.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animecompose.network.responses.MovieCreditResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCreditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieCredit(movieCreditResponse: MovieCreditResponse)


    @Query("select * from MovieCreditResponse where id == :movieId")
    fun getMovieCreditById(movieId: Int): Flow<MovieCreditResponse>
}