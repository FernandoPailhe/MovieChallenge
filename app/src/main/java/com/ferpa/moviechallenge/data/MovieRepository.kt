package com.ferpa.moviechallenge.data

import androidx.compose.runtime.MutableState
import androidx.paging.PagingData
import com.ferpa.moviechallenge.model.MovieDetail
import com.ferpa.moviechallenge.model.MovieTitle
import com.ferpa.moviechallenge.network.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    val error: MutableState<String>

    fun getMovieList(): Flow<PagingData<MovieTitle>>

    fun getMovieDetail(id: Int): Flow<Response<MovieDetail>>

    fun getNetworkMovieDetail(id: Int): Flow<Response<MovieDetail>>

    suspend fun saveLocalMovieDetail(movieDetail: MovieDetail)

    fun getLocalMovieDetail(id: Int): Flow<MovieDetail?>

}