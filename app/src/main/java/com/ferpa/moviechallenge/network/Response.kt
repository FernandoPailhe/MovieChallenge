package com.ferpa.moviechallenge.network

sealed class Response <out T> {

    object Loading: Response<Nothing>()

    data class Succes<out T>(
        val data: T?
    ): Response<T>()

    data class Failure(
        val e: Exception?
    ): Response<Nothing>()

}
