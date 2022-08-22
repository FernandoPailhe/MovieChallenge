package com.ferpa.moviechallenge.fragment.moviedetail



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ferpa.moviechallenge.data.previewsource.MovieDetailPreviewSource
import com.ferpa.moviechallenge.model.MovieDetail
import com.ferpa.moviechallenge.ui.component.MovieDetailCard
import com.ferpa.moviechallenge.ui.theme.MovieChallengeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null
) {
    if (movieDetail == null) return

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Black,
        darkIcons = false)

    MovieDetailCard(movieDetail = movieDetail)

}

@Preview(showBackground = true, widthDp = 380, heightDp = 1200)
@Composable
fun MovieDetailScreenPreview() {
    MovieChallengeTheme {
        MovieDetailScreen(movieDetail = MovieDetailPreviewSource.movieDetailExampleForPreview)
    }
}