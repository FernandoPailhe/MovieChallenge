package com.ferpa.moviechallenge.fragment.moviedetail

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ferpa.moviechallenge.R
import com.ferpa.moviechallenge.network.Response
import com.ferpa.moviechallenge.ui.component.LoadingAnimation
import com.ferpa.moviechallenge.ui.theme.MovieChallengeTheme
import com.ferpa.moviechallenge.utils.Route
import com.ferpa.moviechallenge.viewmodel.MovieDetailViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieDetailFragment(
    modifier: Modifier = Modifier,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    movieId: Int? = null
) {
    if (movieId == null) return

    movieDetailViewModel.getMovieDetail(movieId)

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when (val response = movieDetailViewModel.movieState.value) {
            is Response.Loading -> {
                LoadingAnimation(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is Response.Succes -> {
                MovieDetailScreen(
                    movieDetail = response.data
                )
            }
            is Response.Failure -> {
                Toast.makeText(LocalContext.current, movieDetailViewModel.error.value, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieDetailFragmentPreview() {
    MovieChallengeTheme {
        MovieDetailFragment()
    }
}