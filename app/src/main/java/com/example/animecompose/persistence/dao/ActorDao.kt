package com.example.animecompose.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.animecompose.data.vos.ActorVO
import com.example.animecompose.state.UiState
import kotlinx.coroutines.flow.Flow

@Dao
interface ActorDao {

    @Query("SELECT * FROM ActorVO")
    fun loadAllActors(): Flow<List<ActorVO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<ActorVO>)
}