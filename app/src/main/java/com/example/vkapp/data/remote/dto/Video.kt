package com.example.vkapp.data.remote.dto

import android.net.Uri
import androidx.media3.common.MediaItem
import com.example.vkapp.domain.entity.VideoEntity
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)

fun Video.toVideoEntity() : VideoEntity {

    val uri = Uri.parse(this.sources[0])

    return VideoEntity(
        description = this.description,
        uri = uri,
        mediaItem = MediaItem.fromUri(uri),
        subtitle = this.subtitle,
        thumb = this.thumb,
        title = this.title
    )
}



