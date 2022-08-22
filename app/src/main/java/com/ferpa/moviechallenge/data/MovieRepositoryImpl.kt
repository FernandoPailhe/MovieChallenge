package com.ferpa.moviechallenge.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ferpa.moviechallenge.R
import com.ferpa.moviechallenge.model.MovieDetail
import com.ferpa.moviechallenge.model.MovieTitle
import com.ferpa.moviechallenge.network.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import java.util.*

const val TAG = "MovieRepositoryImpl"

class MovieRepositoryImpl(
    @ApplicationContext private val appContext: Context,
    private val movieDetailDao: MovieDetailDao,
    private val theMovieDbService: TheMovieDbService
) : MovieRepository {

    override val error = mutableStateOf("")

    override fun getMovieDetail(id: Int): Flow<Response<MovieDetail>> = flow {
        val localMovieDetail = getLocalMovieDetail(id).first()
        if (localMovieDetail != null) {
            val localResponse = Response.Succes(localMovieDetail)
            emit(localResponse)
            Log.d(TAG, "Succes Retrieving Local Movie Detail, title -> ${localMovieDetail.title}")
        } else {
            Log.d(TAG, "Local Movie Detail does not exist, retrieving data from internet ")
            getNetworkMovieDetail(id).collect {
                emit(it)
            }
        }
    }

    override fun getMovieList(): Flow<PagingData<MovieTitle>> = Pager(
        config = PagingConfig(
            pageSize = TheMovieDbService.PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            MoviesPagingSource(
                theMovieDbService, context = appContext
            )
        }
    ).flow

    override fun getNetworkMovieDetail(id: Int): Flow<Response<MovieDetail>> = flow {
        try {
            emit(Response.Loading)
            val responseMovieDetail = theMovieDbService.getMovieById(
                id = id,
                language = Locale.getDefault().toLanguageTag()
            )
            if (responseMovieDetail.isSuccessful) {
                emit(Response.Succes(responseMovieDetail.body()))
                try {
                    if (responseMovieDetail.body() != null) {
                        saveLocalMovieDetail(responseMovieDetail.body()!!)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "$e")
                }
            } else {
                when (responseMovieDetail.code()) {
                    401 -> error.value = appContext.getString(R.string.error_401)
                    404 -> error.value = appContext.getString(R.string.error_404)
                }
                emit(Response.Failure(TheMovieDbException(error.value)))
            }
        } catch (e: IOException) {
            Log.e(TAG, "$e")
            error.value = appContext.getString(R.string.IOException)
            emit(Response.Failure(TheMovieDbException(error.value)))
        } catch (e: HttpException) {
            Log.e(TAG, "$e")
            error.value = appContext.getString(R.string.HttpException)
            emit(Response.Failure(TheMovieDbException(error.value)))
        } catch (e: Exception) {
            Log.e(TAG, "$e")
            error.value = appContext.getString(R.string.exception)
            emit(Response.Failure(TheMovieDbException(error.value)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun saveLocalMovieDetail(movieDetail: MovieDetail) {
        movieDetailDao.saveMovieDetail(movieDetail)
    }

    override fun getLocalMovieDetail(id: Int): Flow<MovieDetail?> = movieDetailDao.getLocalMovieDetail(id)

}