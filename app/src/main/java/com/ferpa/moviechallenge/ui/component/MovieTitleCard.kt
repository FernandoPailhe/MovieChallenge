package com.ferpa.moviechallenge.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferpa.moviechallenge.R
import com.ferpa.moviechallenge.model.MovieTitle
import com.ferpa.moviechallenge.network.TheMovieDbService
import com.ferpa.moviechallenge.ui.theme.MovieChallengeTheme
import com.ferpa.moviechallenge.ui.theme.spacing
import com.ferpa.moviechallenge.utils.DEFAULT_POSTER_IMAGE
import com.ferpa.moviechallenge.utils.loadPicture


@Composable
fun MovieTitleCard(
    modifier: Modifier = Modifier,
    movieTitle: MovieTitle?,
    onClickMovieTitle: () -> Unit = {}
) {
    Column {
        Spacer(modifier = Modifier.height(androidx.compose.material3.MaterialTheme.spacing.medium))
        Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClickMovieTitle),
            elevation = 8.dp
        ) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                movieTitle?.let { movieTitle ->
                    movieTitle.poster_path?.let { imgUrl ->
                        val image = loadPicture(
                            imgUrl = TheMovieDbService.IMAGE_BASE_URL + imgUrl,
                            defaultImageResource = DEFAULT_POSTER_IMAGE
                        ).value
                        image?.let { img ->
                            Image(
                                bitmap = img.asImageBitmap(),
                                contentDescription = stringResource(
                                    R.string.content_description_poster_img,
                                    movieTitle
                                ),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                    movieTitle.title?.let { title ->
                        val fontSize = 12
                        Text(
                            modifier = Modifier
                                .padding(androidx.compose.material3.MaterialTheme.spacing.default)
                                .heightIn(min = fontSize.dp * 3)
                                .wrapContentSize(align = Alignment.Center),
                            text = title,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = fontSize.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                        )
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(androidx.compose.material3.MaterialTheme.spacing.default))
    }
}


@Preview(showBackground = true)
@Composable
fun MovieTitleCardPreview() {
    MovieChallengeTheme {
        MovieTitleCard(
            movieTitle = MovieTitle(id = 0, poster_path = null, title = "Movie Title", original_title = "Original Title")
        )
    }
}