package com.ferpa.moviechallenge.fragment.home


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ferpa.moviechallenge.R
import com.ferpa.moviechallenge.ui.component.ErrorMessage
import com.ferpa.moviechallenge.ui.component.LoadingAnimation
import com.ferpa.moviechallenge.ui.component.MovieTitleCard
import com.ferpa.moviechallenge.ui.theme.MovieChallengeTheme
import com.ferpa.moviechallenge.ui.theme.spacing
import com.ferpa.moviechallenge.viewmodel.HomeViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.*


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickToMovieDetailScreen: (Int) -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = true
    )

    val query by homeViewModel.query.collectAsStateWithLifecycle()
    val movieList = homeViewModel.movieListState.collectAsLazyPagingItems()
    val movieResult = homeViewModel.movieSearchResult.value
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardTitleMinWidth = screenWidth * 0.25f
    var isFirstCompose = true

    Column {
        SearchAppBar(
            text = homeViewModel.query.collectAsState().value,
            onTextChange = {
                homeViewModel.onQueryChanged(it)
            },
            onCloseClicked = {
                homeViewModel.onQueryChanged("")
                keyboard?.hide()
                focusManager.clearFocus()
            },
            onSearchClick = {
                homeViewModel.onQueryChanged(it)
                keyboard?.hide()
                focusManager.clearFocus()
            }
        )
        Spacer(
            modifier = Modifier
                .height(2.dp)
        )
        if (movieList == null) return
        val lazyGridState = rememberLazyGridState()

        LazyVerticalGrid(
            modifier = modifier
                .padding(horizontal = androidx.compose.material3.MaterialTheme.spacing.medium)
                .fillMaxWidth(),
            columns = GridCells.Adaptive(minSize = cardTitleMinWidth),
            horizontalArrangement = Arrangement.spacedBy(androidx.compose.material3.MaterialTheme.spacing.medium),
            state = lazyGridState
        ) {

            if (query == "") {
                if (isFirstCompose) {
                    scope.launch {
                        lazyGridState.scrollToItem(
                            homeViewModel.lazyGridScrollToItem.firstVisibleItemIndex,
                            homeViewModel.lazyGridScrollToItem.firstVisibleItemScrollOffset
                        )
                        isFirstCompose = false
                    }
                }
                items(
                    movieList.itemCount
                ) { index ->
                    movieList[index].let { movieTitle ->
                        homeViewModel.addMovieToMoviesAlreadySeen(movieTitle!!)
                        val movieId = movieTitle.id
                        MovieTitleCard(
                            modifier = modifier,
                            movieTitle = movieTitle,
                            onClickMovieTitle = {
                                movieId.let {
                                    homeViewModel.onChangeLazyGridScrollPosition(
                                        lazyGridState
                                    )
                                    onClickToMovieDetailScreen(movieId)
                                }
                            }
                        )
                    }
                }
                movieList.apply {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                    ) {
                        when {
                            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                LoadingAnimation(
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                Row(
                                    modifier = Modifier
                                        .padding(androidx.compose.material3.MaterialTheme.spacing.medium)
                                        .fillMaxWidth(),
                                    horizontalArrangement = SpaceAround
                                ) {
                                    Button(
                                        onClick = {
                                            retry()
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth(0.5f)
                                    ) {
                                        Text(
                                            text = stringResource(R.string.retry_action),
                                            color = Color.White
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            } else {
                if (movieResult.isEmpty()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                    ) {
                        ErrorMessage(error = stringResource(R.string.error_no_found))
                    }
                } else {
                    items(
                        movieResult.size
                    ) { index ->
                        movieResult[index].let { movieTitle ->
                            val movieId = movieTitle.id
                            MovieTitleCard(
                                modifier = modifier,
                                movieTitle = movieTitle,
                                onClickMovieTitle = {
                                    onClickToMovieDetailScreen(movieId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClick: (String) -> Unit
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(androidx.compose.material3.MaterialTheme.spacing.medium),
        value = text,
        onValueChange = { newQuery ->
            onTextChange(newQuery)
        },
        label = {
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = stringResource(R.string.req_search)
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.colors.onBackground
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick(text)
            }
        ),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        trailingIcon = {
            if (text != "") {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            leadingIconColor = Color.LightGray,
            trailingIconColor = Color.DarkGray,
            disabledLabelColor = Color.DarkGray,
            focusedLabelColor = Color.Black
        )
    )

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieChallengeTheme {
        HomeScreen()
    }
}