package com.example.vkapp.ui.video_hosting.list.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkapp.ui.theme.AppTheme
import com.example.vkapp.ui.video_hosting.VideoListState
import com.example.vkapp.ui.video_hosting.list.ListScreenPresenter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    presenter: ListScreenPresenter
) {

    val listState = presenter.listState.collectAsState()

    val refreshState = rememberSwipeRefreshState(listState.value is VideoListState.Loading)

    Scaffold (
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row (
                            modifier = Modifier
                                .padding(end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ){

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = "Видео",
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary
                            )


                        }

                    },
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    ) { innerPadding ->
        SwipeRefresh(
            state = refreshState,
            onRefresh = { presenter.updateListOfVideos() }
        ){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding()),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
            ) {
                if (listState.value is VideoListState.Success){
                    items(items = listState.value.data!!){
                        VideoItem(
                            title = it.title,
                            subtitle = it.subtitle,
                            imageUrl = it.thumb
                        ){
                            presenter.playVideo(it.uri)
                            presenter.navigateToVideo()
                        }
                    }
                } else if (listState.value is VideoListState.Error) {
                    item {
                        Text(
                            text = (listState.value as VideoListState.Error).errorCode.toString() + ": " + (listState.value as VideoListState.Error).errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

            }
        }

    }
}

@Preview
@Composable
private fun ListScreenPreview() {

    val presenter = object : ListScreenPresenter {
        override val listState: StateFlow<VideoListState>
            get() = MutableStateFlow(VideoListState.Error(404, "Ошибка сервера"))

        override fun playVideo(uri: Uri) {

        }

        override fun updateListOfVideos() {

        }

        override fun navigateToVideo() {

        }


    }

    AppTheme {
        ListScreen(presenter)
    }
}