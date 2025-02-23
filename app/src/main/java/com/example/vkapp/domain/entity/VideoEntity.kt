package com.example.vkapp.domain.entity

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoEntity(
    val description: String,
    val uri: Uri,
    val mediaItem: MediaItem,
    val subtitle: String,
    val thumb: String,
    val title: String
)
