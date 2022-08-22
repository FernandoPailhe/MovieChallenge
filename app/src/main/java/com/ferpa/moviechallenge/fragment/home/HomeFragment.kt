package com.ferpa.moviechallenge.fragment.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ferpa.moviechallenge.ui.theme.MovieChallengeTheme


@Composable
fun HomeFragment(
    modifier: Modifier = Modifier,
    onClickToMovieDetailScreen: (Int) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        HomeScreen(
            onClickToMovieDetailScreen = onClickToMovieDetailScreen
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeFragmentPreview() {
    MovieChallengeTheme {
        HomeFragment()
    }
}