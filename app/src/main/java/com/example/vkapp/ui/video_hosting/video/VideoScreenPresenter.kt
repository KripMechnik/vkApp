package com.example.vkapp.ui.video_hosting.video

import androidx.media3.common.Player
import kotlinx.coroutines.flow.StateFlow

interface VideoScreenPresenter {

    val videoData: StateFlow<VideoData>

    val player: Player

    fun nextVideo()

    fun prevVideo()

    fun navigateBack()

}