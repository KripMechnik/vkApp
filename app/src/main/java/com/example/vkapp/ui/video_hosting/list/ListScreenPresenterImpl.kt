package com.example.vkapp.ui.video_hosting.list

import android.net.Uri
import androidx.navigation.NavController
import com.example.vkapp.domain.entity.VideoEntity
import com.example.vkapp.ui.video_hosting.VideoHostingViewModel
import com.example.vkapp.ui.video_hosting.VideoListState
import com.example.vkapp.ui.video_hosting.video.view.VIDEO_SCREEN_ROUTE
import kotlinx.coroutines.flow.StateFlow

class ListScreenPresenterImpl(
    private val viewModel: VideoHostingViewModel,
    private val navController: NavController
) : ListScreenPresenter {
    override val listState: StateFlow<VideoListState>
        get() = viewModel.listState

    override fun playVideo(uri: Uri) {
        viewModel.playVideo(uri)
    }

    override fun updateListOfVideos() {
        viewModel.updateListOfVideos()
    }

    override fun navigateToVideo() {
        navController.navigate(VIDEO_SCREEN_ROUTE)
    }

}