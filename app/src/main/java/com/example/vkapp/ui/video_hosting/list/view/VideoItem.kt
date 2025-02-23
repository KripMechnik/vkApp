package com.example.vkapp.ui.video_hosting.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
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
        AsyncImage(
            modifier = Modifier
                .size(width = 180.dp, height = 105.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = imageUrl,
            contentDescription = "videoPreview",
            placeholder = ColorPainter(MaterialTheme.colorScheme.onPrimary),
            error = ColorPainter(MaterialTheme.colorScheme.onPrimary)
        )

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