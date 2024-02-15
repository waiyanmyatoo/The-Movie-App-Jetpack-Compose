package com.example.animecompose.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.animecompose.data.vos.ActorVO
import com.example.animecompose.data.vos.GenresVO
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.network.responses.MovieCreditResponse
import com.example.animecompose.persistence.converters.MovieVOTypeConverter
import com.example.animecompose.persistence.converters.KnownForVOListTypeConverter
import com.example.animecompose.persistence.converters.MovieCreditTypeConverter
import com.example.animecompose.persistence.dao.ActorDao
import com.example.animecompose.persistence.dao.GenreDao
import com.example.animecompose.persistence.dao.MovieCreditDao
import com.example.animecompose.persistence.dao.MovieDao

@Database(version = 3, entities = arrayOf(ActorVO::class, GenresVO::class, MovieVO::class, MovieCreditResponse::class), exportSchema = false)
@TypeConverters(KnownForVOListTypeConverter::class, MovieVOTypeConverter::class, MovieCreditTypeConverter::class)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun actorDao(): ActorDao

    abstract  fun genreDao(): GenreDao

    abstract fun movieDao(): MovieDao

    abstract fun movieCreditDao(): MovieCreditDao

//    companion object {
//        private var instance: MovieAppDatabase? = null
//
//        fun getInstance( context: Context): MovieAppDatabase {
//            if (instance == null) {
//                instance = Room.databaseBuilder(
//                    context = context,
//                    MovieAppDatabase::class.java,
//                    "TheMovieDB"
//                ).build()
//            }
//            return instance as MovieAppDatabase
//        }
//    }
}