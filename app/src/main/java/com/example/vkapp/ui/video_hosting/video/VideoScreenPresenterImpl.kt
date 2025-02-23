package com.example.vkapp.ui.video_hosting.video

import androidx.media3.common.Player
import androidx.navigation.NavController
import com.example.vkapp.ui.video_hosting.VideoHostingViewModel
import kotlinx.coroutines.flow.StateFlow

class VideoScreenPresenterImpl (
    private val viewModel: VideoHostingViewModel,
    private val navController: NavController
) : VideoScreenPresenter {
    override val videoData: StateFlow<VideoData>
        get() = viewModel.currentVideoDataState

    override val player: Player
        get() = viewModel.player

    override fun nextVideo() {

    }

    override fun prevVideo() {

    }

    override fun navigateBack() {
        player.pause()
        navController.popBackStack()
    }

}