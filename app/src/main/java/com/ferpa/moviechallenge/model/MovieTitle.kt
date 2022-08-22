package com.ferpa.moviechallenge.model

import com.google.gson.annotations.SerializedName

data class MovieTitle(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("original_title")
    val original_title: String?,

    @field:SerializedName("poster_path")
    val poster_path: String?,

    @field:SerializedName("title")
    val title: String?
)

