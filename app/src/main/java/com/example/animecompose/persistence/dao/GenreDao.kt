package com.example.animecompose.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animecompose.data.vos.GenresVO
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Query("select * from GenresVO")
    fun getGenres(): Flow<List<GenresVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(genres: List<GenresVO>)
}