@file:OptIn(ExperimentalAnimationApi::class)

package com.ferpa.moviechallenge

import android.os.Bundle
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.navArgument
import com.ferpa.moviechallenge.fragment.moviedetail.MovieDetailFragment
import com.ferpa.moviechallenge.fragment.home.HomeFragment
import com.ferpa.moviechallenge.utils.Route
import com.ferpa.moviechallenge.ui.theme.MovieChallengeTheme
import com.ferpa.moviechallenge.utils.Const.NAV_ANIMATION_DURATION
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieChallengeTheme {
                MovieChallengeAppScreen()
            }
        }
    }
}

@Composable
fun MovieChallengeAppScreen() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(
            route = Route.Home.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(NAV_ANIMATION_DURATION)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(NAV_ANIMATION_DURATION)
                )
            },
        ) {
            HomeFragment(
                onClickToMovieDetailScreen = { movieId ->
                    navController.navigate(
                        Route.MovieDetail.createRoute(movieId)
                    )
                }
            )
        }
        composable(
            route = Route.MovieDetail.route,
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(NAV_ANIMATION_DURATION)
                )
            },
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(NAV_ANIMATION_DURATION)
                )
            },
            arguments = listOf(
                navArgument("movieId") {
                    type = androidx.navigation.NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt("movieId")
            requireNotNull(movieId) { "movieId parameter was´t found" }
            MovieDetailFragment(movieId = movieId)
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun JetpackComposeAppScreenPreview() {
    MovieChallengeTheme {
        MovieChallengeAppScreen()
    }
}

