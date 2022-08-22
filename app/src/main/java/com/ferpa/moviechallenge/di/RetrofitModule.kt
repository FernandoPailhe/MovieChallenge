package com.ferpa.moviechallenge.di

import com.ferpa.moviechallenge.network.TheMovieDbService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideTheMovieDbService(): TheMovieDbService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(TheMovieDbService.BASE_URL)
            .build()
            .create(TheMovieDbService::class.java)
    }

}