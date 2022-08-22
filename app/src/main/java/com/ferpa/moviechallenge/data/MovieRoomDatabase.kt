package com.ferpa.moviechallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ferpa.moviechallenge.data.utils.Converters
import com.ferpa.moviechallenge.model.MovieDetail


@Database(
    version = 1,
    entities = [
        MovieDetail::class
    ]
)
@TypeConverters(Converters::class)
abstract class MovieRoomDatabase: RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "movie_db"
    }

    abstract fun movieDetailDao(): MovieDetailDao

}
