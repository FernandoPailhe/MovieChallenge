package com.ferpa.moviechallenge.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorMessage (
    modifier: Modifier = Modifier,
    error: String
) {
    Text(text = error)
}