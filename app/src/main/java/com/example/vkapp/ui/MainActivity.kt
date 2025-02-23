package com.example.vkapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.vkapp.ui.theme.AppTheme
import com.example.vkapp.ui.video_hosting.list.view.LIST_SCREEN_ROUTE
import com.example.vkapp.ui.video_hosting.list.view.listScreen
import com.example.vkapp.ui.video_hosting.video.view.videoScreen
import dagger.hilt.android.AndroidEntryPoint

const val NAVIGATION_GRAPH = "navgraph"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppTheme {
                NavHost(navController = navController, startDestination = NAVIGATION_GRAPH) {
                    navigation(
                        startDestination = LIST_SCREEN_ROUTE,
                        route = NAVIGATION_GRAPH
                    ){
                        listScreen(navController)
                        videoScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
) : T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}