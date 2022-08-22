package com.ferpa.moviechallenge.data

import androidx.room.*
import com.ferpa.moviechallenge.model.MovieDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieDetail(movieDetail: MovieDetail)

    @Query("SELECT * from MovieDetail WHERE id = :id")
    fun getLocalMovieDetail(id: Int): Flow<MovieDetail?>

}