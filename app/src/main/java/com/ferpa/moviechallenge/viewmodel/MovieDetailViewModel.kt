package com.ferpa.moviechallenge.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferpa.moviechallenge.data.MovieRepository
import com.ferpa.moviechallenge.model.MovieDetail
import com.ferpa.moviechallenge.network.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieDetailState = mutableStateOf<Response<MovieDetail>>(Response.Succes(null))
    val movieState: State<Response<MovieDetail>> = _movieDetailState

    val error = movieRepository.error

    fun getMovieDetail(id: Int){
        viewModelScope.launch {
            movieRepository.getMovieDetail(id).collect{ response ->
                _movieDetailState.value = response
            }
        }
    }

}