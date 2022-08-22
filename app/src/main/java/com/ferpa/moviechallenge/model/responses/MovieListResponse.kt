package com.ferpa.moviechallenge.model.responses

import com.ferpa.moviechallenge.model.MovieTitle
import com.google.gson.annotations.SerializedName

data class MovieListResponse(

    @field: SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<MovieTitle>

)
