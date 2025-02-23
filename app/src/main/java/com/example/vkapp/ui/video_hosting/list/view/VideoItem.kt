package com.example.vkapp.ui.video_hosting.list.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.vkapp.ui.theme.AppTheme

@Composable
fun VideoItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imageUrl: String,
    duration: Long? = null,
    onClick: () -> Unit
    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start)
    ){
        Box(
            modifier = Modifier
                .size(width = 180.dp, height = 105.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                model = imageUrl,
                contentDescription = "videoPreview",
                placeholder = ColorPainter(MaterialTheme.colorScheme.onPrimary),
                contentScale = ContentScale.Crop,
                error = ColorPainter(MaterialTheme.colorScheme.onPrimary)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)

                    .background(color = Color.Black.copy(alpha = 0.6f))
                    .padding(4.dp),
                text = formatDuration(duration ?: 0),
                color = Color.White
            )
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

fun formatDuration(milliseconds: Long): String {
    if (milliseconds.toInt() == 0) return "--:--"
    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

@Preview
@Composable
private fun ListItemPreview() {
    AppTheme {
        VideoItem(
            title = "Название",
            subtitle = "Описание",
            imageUrl = ""
        ){}
    }
}