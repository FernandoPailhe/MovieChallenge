package com.ferpa.moviechallenge.di

import android.content.Context
import com.ferpa.moviechallenge.data.MovieDetailDao
import com.ferpa.moviechallenge.data.MovieRepository
import com.ferpa.moviechallenge.data.MovieRepositoryImpl
import com.ferpa.moviechallenge.network.TheMovieDbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        @ApplicationContext context: Context,
        movieDetailDao: MovieDetailDao,
        theMovieDbService : TheMovieDbService
    ): MovieRepository = MovieRepositoryImpl(
        context,
        movieDetailDao,
        theMovieDbService
    )


}