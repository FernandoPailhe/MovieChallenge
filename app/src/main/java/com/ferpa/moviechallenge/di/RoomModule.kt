package com.ferpa.moviechallenge.di

import android.content.Context
import androidx.room.Room
import com.ferpa.moviechallenge.data.MovieDetailDao
import com.ferpa.moviechallenge.data.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideMovieRoomDatabase(@ApplicationContext context: Context): MovieRoomDatabase {
        return Room.databaseBuilder(
            context,
            MovieRoomDatabase::class.java,
            MovieRoomDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDetailDao(movieRoomDatabase: MovieRoomDatabase): MovieDetailDao {
        return movieRoomDatabase.movieDetailDao()
    }
}
