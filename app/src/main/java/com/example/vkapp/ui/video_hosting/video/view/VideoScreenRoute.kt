package com.example.vkapp.ui.video_hosting.video.view

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.vkapp.ui.sharedViewModel
import com.example.vkapp.ui.video_hosting.VideoHostingViewModel
import com.example.vkapp.ui.video_hosting.video.VideoScreenPresenterImpl

const val VIDEO_SCREEN_ROUTE = "video_screen_route"

fun NavGraphBuilder.videoScreen(navController: NavHostController){
    composable(VIDEO_SCREEN_ROUTE){ entry ->

        val viewModel = entry.sharedViewModel<VideoHostingViewModel>(navController)
        val presenter = VideoScreenPresenterImpl(viewModel, navController)

        VideoScreen(presenter)
    }
}