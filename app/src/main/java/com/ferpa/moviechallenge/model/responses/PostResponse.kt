package com.ferpa.moviechallenge.model.responses

import com.google.gson.annotations.SerializedName

data class PostResponse(

    @field:SerializedName("status_code")
    val status_code : Int,

    @field:SerializedName("status_message")
    val status_message : String

)