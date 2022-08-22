package com.ferpa.moviechallenge.utils

sealed class Route ( val route: String){
    object Home: Route("Home")
    object MovieDetail: Route("MovieDetail/{movieId}"){
        fun createRoute(movieId: Int) = "MovieDetail/$movieId"
    }
}
