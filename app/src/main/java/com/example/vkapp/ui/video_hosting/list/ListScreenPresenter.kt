package com.example.vkapp.ui.video_hosting.list

import android.net.Uri
import com.example.vkapp.ui.video_hosting.VideoListState
import kotlinx.coroutines.flow.StateFlow

interface ListScreenPresenter {

    val listState: StateFlow<VideoListState>

    fun playVideo(uri: Uri)

    fun updateListOfVideos()

    fun navigateToVideo()

}