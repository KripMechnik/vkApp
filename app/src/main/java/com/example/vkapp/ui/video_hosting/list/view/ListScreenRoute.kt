package com.example.vkapp.ui.video_hosting.list.view

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.vkapp.ui.sharedViewModel
import com.example.vkapp.ui.video_hosting.VideoHostingViewModel
import com.example.vkapp.ui.video_hosting.list.ListScreenPresenterImpl

const val LIST_SCREEN_ROUTE = "list_screen_route"

fun NavGraphBuilder.listScreen(navController: NavHostController){
    composable(LIST_SCREEN_ROUTE){ entry ->

        val viewModel = entry.sharedViewModel<VideoHostingViewModel>(navController)
        val presenter = ListScreenPresenterImpl(viewModel, navController)

        ListScreen(presenter)
    }
}