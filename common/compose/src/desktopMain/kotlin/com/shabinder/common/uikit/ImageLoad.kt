package com.shabinder.common.uikit


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.shabinder.common.di.Picture
import com.shabinder.common.di.dispatcherIO
import kotlinx.coroutines.withContext

@Composable
actual fun ImageLoad(
    link: String,
    loader: suspend (String) -> Picture,
    desc: String,
    modifier: Modifier,
    // placeholder: ImageVector
) {
    var pic by remember(link) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(link) {
        withContext(dispatcherIO) {
            pic = loader(link).image
        }
    }

    Crossfade(pic) {
        if (it == null) Image(PlaceHolderImage(), desc, modifier, contentScale = ContentScale.Crop) else Image(it, desc, modifier, contentScale = ContentScale.Crop)
    }
}
