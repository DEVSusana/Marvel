package com.proof.marvel.view.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.proof.marvel.data.model.Result


@ExperimentalCoilApi
@Composable
fun ImageMarvel(detail: Result) {
    Image(
        painter = rememberImagePainter(
            detail.thumbnail.let {
                "${detail.thumbnail.path}.${detail.thumbnail.extension}"
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .padding(4.dp)
            .height(140.dp)
            .width(140.dp)
            .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
    )

}