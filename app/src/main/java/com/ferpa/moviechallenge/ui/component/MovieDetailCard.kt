package com.ferpa.moviechallenge.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.ferpa.moviechallenge.R
import com.ferpa.moviechallenge.data.previewsource.MovieDetailPreviewSource
import com.ferpa.moviechallenge.model.*
import com.ferpa.moviechallenge.network.TheMovieDbService
import com.ferpa.moviechallenge.ui.theme.MovieChallengeTheme
import com.ferpa.moviechallenge.ui.theme.spacing
import com.ferpa.moviechallenge.utils.*
import com.ferpa.moviechallenge.utils.Const.shapes
import com.google.accompanist.insets.LocalWindowInsets
import kotlin.math.max
import kotlin.math.min

@Composable
fun MovieDetailCard(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail
) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Content(movieDetail, scrollState)
        MovieDetailToolBar(movieDetail, scrollState)
    }

}

@Composable
fun MovieDetailToolBar(movieDetail: MovieDetail, scrollState: LazyListState) {

    /*
    Parallax effect values
     */
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val topBarExtendedHeight = screenHeight * 0.25f
    val topBarCollapsedHeight = topBarExtendedHeight * 0.25f
    val backDropHeight = topBarExtendedHeight - topBarCollapsedHeight
    val movieTitleHeight = topBarCollapsedHeight * 1.25f
    val starRowHeight = backDropHeight * 0.4f
    val maxOffset = with(LocalDensity.current) {
        (backDropHeight - movieTitleHeight * 0.85f).roundToPx()
    } - LocalWindowInsets.current.systemBars.layoutInsets.top
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        modifier = Modifier
            .height(backDropHeight + movieTitleHeight)
            .fillMaxWidth()
            .offset {
                IntOffset(x = 0, y = -offset)
            },
        contentPadding = PaddingValues(),
        backgroundColor = Color.Black,
        elevation = if (offset == maxOffset) 8.dp else 0.dp
    ) {

        Column {

            Spacer(modifier = Modifier.height(topBarCollapsedHeight/4))

            Box(
                modifier = Modifier.height(backDropHeight),
            ) {

                BackDropImage(backdrop_path = movieDetail.backdrop_path)

                Box(
                    modifier = Modifier
                        .height(backDropHeight * 0.5f)
                        .align(Alignment.BottomCenter),
                ) {
                    BottomGradient()
                }

                YearCard(year = movieDetail.getReleaseYear(), offsetProgress)

            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .height(movieTitleHeight),
                verticalArrangement = Arrangement.Center
            ) {
                MovieTitleLayout(title = movieDetail.title, offsetProgress)
            }

        }

    }

    Box(
        modifier = Modifier
            .height(starRowHeight)
    ) {

        StarGradient()

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally,
        ) {
            RateStarsRow(rate = movieDetail.getRate())

            //BottomStarGradient(offsetProgress, starRowHeight)
        }


    }

}

@Composable
fun Content(movieDetail: MovieDetail, scrollState: LazyListState) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val topBarExtendedHeight = screenHeight * 0.25f
    val topBarCollapsedHeight = topBarExtendedHeight * 0.25f

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top =  topBarExtendedHeight + topBarCollapsedHeight/4, bottom = androidx.compose.material3.MaterialTheme.spacing.large),
        state = scrollState
    ) {
        item {

            Tagline(tagline = movieDetail.tagline)

            IconsRow(movieDetail)

            GenreList(genres = movieDetail.genres)

            Overview(overview = movieDetail.overview)

            PosterCard(
                imgUrl = movieDetail.poster_path,
                movieTitle = movieDetail.title.toString()
            )

            ProductionDetailsRow(details = movieDetail.getDetailsList())

            Spacer(modifier = Modifier.height(androidx.compose.material3.MaterialTheme.spacing.medium))

            ListRow(
                data = movieDetail.getSpokenLanguagesList(),
                title = stringResource(R.string.spoken_languages),
            )

            ListRow(
                data = movieDetail.getProductionCompanyList(),
                title = stringResource(R.string.production_companies),
            )

            ListRow(
                data = movieDetail.getProductionCountryList(),
                title = stringResource(R.string.production_countries),
            )

        }
    }
}

@Composable
fun StarGradient() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        Pair(
                            0.1f,
                            Color.Black
                        ),
                        Pair(
                            1f,
                            Color.Transparent
                        )
                    )
                )
            )
    )
}

@Composable
fun RateStarsRow(rate: Float?) {
    if (rate == null) return


    Box(modifier = Modifier) {
        StarsRow(rate = 1f, tint = Color.DarkGray)
        StarsRow(rate, tint = Color.White)
    }
}

@Composable
fun StarsRow(rate: Float, tint: Color){
    Row(
        modifier = Modifier
            .clip(HalfSizeShape(widthPart = rate))
            .padding(
                top = androidx.compose.material3.MaterialTheme.spacing.top,
                bottom = androidx.compose.material3.MaterialTheme.spacing.default
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (star in 0..9){
            Star(tint)
        }
    }
}

@Composable
fun Star(tint: Color){
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_star_rate_24),
        contentDescription = null,
        tint = tint
    )
}

@Composable
fun BackDropImage(backdrop_path: String?) {

    val imgUrl: String? =
        if (backdrop_path == null) null else TheMovieDbService.IMAGE_BASE_URL + backdrop_path

    val image = loadPicture(
        imgUrl = imgUrl,
        defaultImageResource = DEFAULT_BACKDROP_IMAGE
    ).value

    image?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

@Composable
fun BottomGradient() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        Pair(
                            0.1f,
                            Color.Transparent
                        ),
                        Pair(
                            1f,
                            Color.Black
                        )
                    )
                )
            )
    )
}

@Composable
fun PosterCard(imgUrl: String?, movieTitle: String) {
    if (imgUrl == null) return
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(androidx.compose.material3.MaterialTheme.spacing.medium),
            elevation = 8.dp
        ) {

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
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shapes.small),
                )
            }
        }
    }
}

@Composable
fun MovieTitleLayout(title: String?, offsetProgress: Float) {
    if (title == null) return
    var textSize = 22

    if (title.length > 25) {
        textSize -= (title.length / 10)
    }
    Text(
        text = title,
        fontSize = textSize.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Visible,
        modifier = Modifier
            .padding(horizontal = androidx.compose.material3.MaterialTheme.spacing.medium)
            .background(Color.Transparent)
            .scale(1f - 0.12f * offsetProgress)
    )
}

@Composable
fun YearCard(year: String?, offsetProgress: Float) {
    if (year == null) return
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                androidx.compose.material3.MaterialTheme.spacing.extraSmall
            ),
        verticalAlignment = Alignment.Bottom
    ) {
        Card(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(
                    vertical = androidx.compose.material3.MaterialTheme.spacing.default,
                    horizontal = androidx.compose.material3.MaterialTheme.spacing.default
                )
                .shadow(
                    elevation = 50.dp,
                    ambientColor = Color.DarkGray,
                    spotColor = Color.LightGray
                )
                .alpha(1f - offsetProgress),
            shape = CutCornerShape(
                topStart = 16.dp,
                bottomStart = 4.dp,
                topEnd = 4.dp,
                bottomEnd = 4.dp
            ),
            border = BorderStroke(1.dp, Color.DarkGray),
            backgroundColor = Color.Transparent,
            //elevation = 2.dp
        ) {
            Text(
                text = year,
                fontWeight = FontWeight.Bold,
                color = Color.LightGray,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(
                        vertical = androidx.compose.material3.MaterialTheme.spacing.default,
                        horizontal = androidx.compose.material3.MaterialTheme.spacing.medium
                    )

            )
        }
    }
}

@Composable
fun Tagline(tagline: String?) {
    if (tagline == "" || tagline == null) return

    Text(
        text = tagline,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Italic,
        modifier = Modifier
            .padding(androidx.compose.material3.MaterialTheme.spacing.medium)
    )

}

@Composable
fun IconsRow(movieDetail: MovieDetail) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = androidx.compose.material3.MaterialTheme.spacing.medium)
    ) {
        IconColumn(R.drawable.ic_baseline_ondemand_video_24, movieDetail.getMovieRuntime())
        RateIconColumn(
            R.drawable.ic_baseline_star_rate_24,
            movieDetail.getVoteAverage(),
            movieDetail.getRate()
        )
        IconColumn(R.drawable.ic_baseline_stats, movieDetail.getVotes())
    }
}

@Composable
fun IconColumn(@DrawableRes icon: Int, text: String?) {
    if (text == null) return

    Column(horizontalAlignment = CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Text(modifier = Modifier
            .align(CenterHorizontally),
            text = text,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RateIconColumn(@DrawableRes icon: Int, text: String?, rate: Float?) {
    Column(horizontalAlignment = CenterHorizontally) {
        Box {
            if (rate == null) return
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .clip(HalfSizeShape(heightPart = (1f - rate)))
            )
        }
        if (text == null) return
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun GenreList(genres: List<Genre?>?) {
    if (genres == null || genres.isEmpty()) return

    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = androidx.compose.material3.MaterialTheme.spacing.medium)
            .horizontalScroll(scrollState, true),
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier
                .width(androidx.compose.material3.MaterialTheme.spacing.medium)
        )
        genres.forEach {
            it?.name?.let { it1 -> GenreCard(genre = it1) }
        }
        Spacer(
            modifier = Modifier
                .width(androidx.compose.material3.MaterialTheme.spacing.medium)
        )
    }
}

@Composable
fun GenreCard(genre: String) {
    Card(
        modifier = Modifier
            .padding(androidx.compose.material3.MaterialTheme.spacing.default),
        shape = shapes.small,
        elevation = 8.dp
    ) {
        Text(
            text = genre,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(androidx.compose.material3.MaterialTheme.spacing.default)
        )
    }
}

@Composable
fun Overview(overview: String?) {
    if (overview == null || overview.length < 2) return
    Column(
        modifier = Modifier
    ) {
        Text(
            text = stringResource(R.string.overview),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = androidx.compose.material3.MaterialTheme.spacing.medium,
                    vertical = androidx.compose.material3.MaterialTheme.spacing.default)
        )
        Text(
            text = overview,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .padding(
                    horizontal = androidx.compose.material3.MaterialTheme.spacing.medium,
                    vertical = androidx.compose.material3.MaterialTheme.spacing.default)
        )
    }
}

@Composable
fun ProductionDetailsRow(details: List<Pair<Int, String>>) {
    val scrollState = rememberScrollState()
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = androidx.compose.material3.MaterialTheme.spacing.medium)
            .horizontalScroll(scrollState, true)
    ) {
        details.forEach {
            TextItem(stringResource(it.first), it.second)
        }
    }
}

@Composable
fun ListRow(data: List<String?>?, title: String? = null) {
    if (data == null || data.isEmpty()) return
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        val scrollState = rememberScrollState()
        if (title != null) {
            Text(
                text = title, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = androidx.compose.material3.MaterialTheme.spacing.default)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = androidx.compose.material3.MaterialTheme.spacing.extraSmall)
                .horizontalScroll(scrollState, true)
        ) {
            data.forEach {
                TextItem(null, it)
            }
        }
    }
}

@Composable
fun TextItem(title: String?, content: String?) {
    if (content == null) return
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        if (title != null) {
            Text(
                text = title, fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
                        horizontal = androidx.compose.material3.MaterialTheme.spacing.medium,
                        vertical = androidx.compose.material3.MaterialTheme.spacing.extraSmall)
            )
        }
        Text(
            text = content,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .padding(
                    horizontal = androidx.compose.material3.MaterialTheme.spacing.medium,
                    vertical = androidx.compose.material3.MaterialTheme.spacing.extraSmall)
        )

    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 720)
@Composable
fun MovieDetailScreenPreview() {
    MovieChallengeTheme {
        MovieDetailCard(movieDetail = MovieDetailPreviewSource.movieDetailExampleForPreview)
    }
}