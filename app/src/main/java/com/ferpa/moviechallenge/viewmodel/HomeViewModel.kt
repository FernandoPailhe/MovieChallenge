package com.ferpa.moviechallenge.viewmodel


import android.util.Log
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ferpa.moviechallenge.data.MovieRepository
import com.ferpa.moviechallenge.model.MovieTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    val movieListState: Flow<PagingData<MovieTitle>> =
        movieRepository.getMovieList().cachedIn(viewModelScope)

    var lazyGridScrollToItem = LazyGridState(0, 0)

    private val _moviesAlreadySeen = MutableStateFlow<MutableList<MovieTitle>>(mutableListOf())
    private val moviesAlreadySeen: StateFlow<MutableList<MovieTitle>> get() = _moviesAlreadySeen

    val query = MutableStateFlow("")

    val movieSearchResult: MutableState<List<MovieTitle>> = mutableStateOf(listOf())

    private suspend fun getSearchResult() {
        movieSearchResult.value =
            moviesAlreadySeen.first().filter { movieTitle ->
                movieTitle.title?.let {
                    filterTitle(it, movieTitle.original_title)
                }!!
            }
    }

    private fun filterTitle(title: String, original_title: String?): Boolean {
        return if (original_title != null) {
            return (title.contains(query.value, true) || original_title.contains(query.value, true))
        } else {
            return (title.contains(query.value, true))
        }
    }

    fun onChangeLazyGridScrollPosition(lazyGridState: LazyGridState) {
        lazyGridScrollToItem = lazyGridState
    }

    fun addMovieToMoviesAlreadySeen(movieTitle: MovieTitle) {
        if (!moviesAlreadySeen.value.contains(movieTitle)) {
            _moviesAlreadySeen.value.add(movieTitle)
        }
        Log.d(TAG, moviesAlreadySeen.value.size.toString())
    }

    fun onQueryChanged(newQuery: String) {
        this.query.value = newQuery
        if (newQuery != "") {
            viewModelScope.launch {
                getSearchResult()
            }
        }
    }

    /* This function also search the unloaded at the pages
     *
     *   val movieSearchAtAll by lazy {
     *      query.flatMapLatest { query ->
     *          movieListState.map { pager ->
     *              pager.filter { movieTitle ->
     *                  movieTitle.title.let {
     *                     movieTitle.title!!.contains(query.value, true)
     *                  }
     *              }
     *          }
     *      }.cachedIn(viewModelScope)
     *    }
     */

}