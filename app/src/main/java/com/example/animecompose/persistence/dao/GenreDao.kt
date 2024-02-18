package com.example.animecompose.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animecompose.data.vos.GenresVO
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Query("select * from GenresVO order by name asc")
    fun getGenres(): Flow<List<GenresVO>>

    @Query("select * from GenresVO order by name asc")
    suspend fun getGenresOneTime(): List<GenresVO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(genres: List<GenresVO>)
}