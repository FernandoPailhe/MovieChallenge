package com.ferpa.moviechallenge.data.previewsource

import com.ferpa.moviechallenge.model.*

object MovieDetailPreviewSource {

    val movieDetailExampleForPreview = MovieDetail(
        adult = false,
        backdrop_path = "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
        budget = 63000000,
        genres = listOf(Genre(18, "Drama")),
        homepage = "",
        id = 550,
        imdb_id = "tt0137523",
        original_language = "en",
        original_title = "Fight Club",
        overview = "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
        popularity = 0.5,
        poster_path = null,
        production_companies = listOf(
            ProductionCompany(508, "/7PzJdsLGlR7oW4J0J5Xcd0pHGRg.png", "Regency Enterprises", "US"),
            ProductionCompany(711, null, "Fox 2000 Pictures", ""),
            ProductionCompany(25, "/qZCc1lty5FzX30aOCVRBLzaVmcp.png", "20th Century Fox", "US")
        ),
        production_countries = listOf(ProductionCountry("US", "United States of America")),
        release_date = "1999-10-12",
        revenue = 100853753,
        runtime = 139,
        spoken_languages = listOf(SpokenLanguage("en", "English")),
        status = "Released",
        tagline = "How much can you know about yourself if you've never been in a fight?",
        title = "Fight Club",
        video = false,
        vote_average = 7.8,
        vote_count = 3439
    )

}
