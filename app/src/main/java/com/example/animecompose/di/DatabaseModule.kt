package com.example.animecompose.di

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Room
import androidx.room.migration.AutoMigrationSpec
import com.example.animecompose.data.models.Impls.MovieModelImpl
import com.example.animecompose.data.models.MovieModel
import com.example.animecompose.persistence.MovieAppDatabase
import com.example.animecompose.persistence.dao.ActorDao
import com.example.animecompose.persistence.dao.GenreDao
import com.example.animecompose.persistence.dao.MovieCreditDao
import com.example.animecompose.persistence.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieAppDatabase {
        synchronized(this) {
            val instance = Room.databaseBuilder(
                context = context,
                MovieAppDatabase::class.java,
                "the_movie.db"
            ).fallbackToDestructiveMigration()

                .build()
            return instance
        }
    }

    @Singleton
    @Provides
    fun provideActorDao(movieAppDatabase: MovieAppDatabase): ActorDao {
        return movieAppDatabase.actorDao()
    }

    @Singleton
    @Provides
    fun provideGenreDao(movieAppDatabase: MovieAppDatabase): GenreDao {
        return movieAppDatabase.genreDao()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieAppDatabase: MovieAppDatabase): MovieDao {
        return movieAppDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideMovieCreditDao(movieAppDatabase: MovieAppDatabase): MovieCreditDao {
        return movieAppDatabase.movieCreditDao()
    }


    @Singleton
    @Provides
    fun provideMovieModel(actorDao: ActorDao, genreDao: GenreDao, movieDao: MovieDao, movieCreditDao: MovieCreditDao): MovieModel =
        MovieModelImpl(actorDao = actorDao, genreDao = genreDao, movieDao, movieCreditDao)


}