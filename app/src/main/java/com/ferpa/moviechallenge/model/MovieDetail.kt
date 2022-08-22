package com.ferpa.moviechallenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ferpa.moviechallenge.R
import java.text.DecimalFormat


@Entity
data class MovieDetail(
    @PrimaryKey
    val id: Int,
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val budget: Long? = null,
    val genres: List<Genre?>? = null,
    val homepage: String? = null,
    val imdb_id: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val production_companies: List<ProductionCompany?>? = null,
    val production_countries: List<ProductionCountry?>? = null,
    val release_date: String? = null,
    val revenue: Long? = null,
    val runtime: Int? = null,
    val spoken_languages: List<SpokenLanguage?>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Long? = null
)

fun MovieDetail.getMovieRuntime(): String? {
    if (this.runtime == null || this.runtime == 0 ) return null

    val hour: Int = this.runtime / 60
    val minutes: Int = this.runtime % 60
    return "${hour}h ${minutes}m"
}

fun MovieDetail.getRate(): Float? = if (this.vote_average != null) (this.vote_average / 10).toFloat() else null

fun MovieDetail.getVoteAverage(): String? {
    if (this.vote_average == null) return null

    val fiveStarAverage: Double = this.vote_average
    val numberFormat = DecimalFormat("#.#")
    return numberFormat.format(fiveStarAverage)

}

fun MovieDetail.getReleaseYear(): String? = this.release_date?.substring(0, 4)

fun MovieDetail.getVotes(): String? {
    return if (this.vote_count == null) {
        null
    } else {
        "${this.vote_count} Votes"
    }
}

private fun MovieDetail.getStatus(): Pair<Int, String>? = if (this.status == null || this.status == "") null else Pair(R.string.status, this.status)

private fun MovieDetail.getReleaseDate(): Pair<Int, String>? = if (this.release_date == null || this.release_date == "") null else Pair(R.string.release_date, this.release_date)

private fun MovieDetail.getRevenue(): Pair<Int, String>? = if (this.revenue == null || this.revenue < 1) null else Pair(R.string.revenue, currencyFormat(this.revenue))

private fun MovieDetail.getBudget(): Pair<Int, String>? = if (this.budget == null || this.budget < 1) null else Pair(R.string.budget, currencyFormat(this.budget))

private fun MovieDetail.getOriginalLanguage(): Pair<Int, String>? {
    val language = this.spoken_languages?.find {
        it?.iso_639_1.equals(this.original_language)
    }?.name
    return if (language == null) null else Pair(R.string.original_language, language)
}

private fun MovieDetail.getOriginalTitle(): Pair<Int, String>? = if (this.title.equals(this.original_title)) null else Pair(
        R.string.original_title,
        this.original_title.toString()
    )

fun MovieDetail.getDetailsList(): List<Pair<Int, String>> {

    val detailList: MutableList<Pair<Int, String>> = mutableListOf()

    if (this.getStatus() != null) detailList.add(this.getStatus()!!)
    if (this.getReleaseDate() != null) detailList.add(this.getReleaseDate()!!)
    if (this.getOriginalTitle() != null) detailList.add(this.getOriginalTitle()!!)
    if (this.getOriginalLanguage() != null) detailList.add(this.getOriginalLanguage()!!)
    if (this.getBudget() != null) detailList.add(this.getBudget()!!)
    if (this.getRevenue() != null) detailList.add(this.getRevenue()!!)

    return detailList
}

fun MovieDetail.getSpokenLanguagesList(): List<String?>? = spoken_languages?.map { it?.name }

fun MovieDetail.getProductionCompanyList(): List<String?>?= production_companies?.map { it?.name }

//fun MovieDetail.getProductionCompanyList(): List<Pair<String?, String?>>?= production_companies?.map { Pair(it?.name, it?.logo_path) }

fun MovieDetail.getProductionCountryList(): List<String?>? = production_countries?.map { it?.name }

private fun currencyFormat(number: Long): String {
    val numberFormat = DecimalFormat("#,###")
    return "USD ${numberFormat.format(number)}"
}


