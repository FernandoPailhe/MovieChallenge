package com.ferpa.moviechallenge.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ferpa.moviechallenge.R
import com.ferpa.moviechallenge.model.MovieTitle
import retrofit2.HttpException
import java.io.IOException
import java.util.*

const val TAG = "MoviesPagingSource"

class MoviesPagingSource(
    private val theMovieDbService: TheMovieDbService,
    private val context: Context
) : PagingSource<Int, MovieTitle>() {

    override fun getRefreshKey(state: PagingState<Int, MovieTitle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieTitle> {
        return try {
            val position = params.key ?: 1
            val response = theMovieDbService.getMovieList(language = Locale.getDefault().toLanguageTag(), page = position)
            if (response.isSuccessful) {
                Log.d(TAG, "Response is Successful")
                LoadResult.Page(
                    data = response.body()!!.results,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = position + 1
                )
            } else {
                var error = response.errorBody().toString()
                when (response.code()){
                    401 -> error = context.getString(R.string.error_401)
                    404 -> error = context.getString(R.string.error_404)
                }
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                LoadResult.Error(TheMovieDbException(error))
            }
        } catch (e: IOException){
            Log.e(TAG, e.toString())
            val ioException = context.getString(R.string.IOException)
            Toast.makeText(context, ioException, Toast.LENGTH_LONG).show()
            return LoadResult.Error(TheMovieDbException(ioException))
        } catch (e: HttpException){
            val httpException = context.getString(R.string.HttpException)
            Log.e(TAG, e.toString())
            Toast.makeText(context, httpException, Toast.LENGTH_LONG).show()
            return LoadResult.Error(TheMovieDbException(httpException))
        } catch (e: Exception){
            Log.e(TAG, e.toString())
            return LoadResult.Error(e)
        }
    }

}