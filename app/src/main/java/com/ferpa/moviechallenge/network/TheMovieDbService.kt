package com.ferpa.moviechallenge.network


import com.ferpa.moviechallenge.model.MovieDetail
import com.ferpa.moviechallenge.model.responses.MovieListResponse
import retrofit2.Response
import retrofit2.http.*

interface TheMovieDbService {

    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("api_key") key: String = KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") key: String = KEY
    ): Response<MovieDetail>

    companion object {
        const val KEY = "173ef8e2c408c44a43a36cfca4fe552f"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val PAGE_SIZE = 20
    }

}