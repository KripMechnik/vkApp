package com.example.vkapp.ui.video_hosting

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.vkapp.common.Resource
import com.example.vkapp.data.remote.dto.toVideoEntity
import com.example.vkapp.domain.entity.VideoEntity
import com.example.vkapp.domain.usecases.GetListOfVideosUseCase
import com.example.vkapp.ui.video_hosting.video.VideoData
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_example_vkapp_ui_video_hosting_VideoHostingViewModel_HiltModules_BindsModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoHostingViewModel @OptIn(UnstableApi::class)
@Inject constructor(
    private val getListOfVideosUseCase: GetListOfVideosUseCase,
    val player: Player
) : ViewModel() {

    private val _listState = MutableStateFlow<VideoListState>(VideoListState.Empty)
    val listState = _listState.asStateFlow()

    private val _currentVideoDataState = MutableStateFlow(VideoData("", "", ""))
    val currentVideoDataState = _currentVideoDataState.asStateFlow()

    private val retriever = MediaMetadataRetriever()

    init {
        player.addListener(
            object : Player.Listener{
                override fun onPositionDiscontinuity(
                    oldPosition: Player.PositionInfo,
                    newPosition: Player.PositionInfo,
                    reason: Int
                ) {
                    super.onPositionDiscontinuity(oldPosition, newPosition, reason)
                    setVideoData(newPosition.mediaItemIndex)
                }
            }
        )
        player.prepare()
        getListOfVideos()
    }

    private fun addMediaItems(items: List<MediaItem>){
        player.addMediaItems(items)
        player.prepare()
    }

    private fun releaseMediaItems(){
        player.removeMediaItems(0, player.mediaItemCount)
    }

    fun playVideo(uri: Uri){
        if (_listState.value is VideoListState.Success){
            _listState.value.data!!.forEachIndexed { index, videoEntity ->
                if (videoEntity.uri == uri) {
                    player.seekToDefaultPosition(index)
                    setVideoData(index)
                }
            }
            player.play()
        }

    }

    private fun getVideosDuration(){
        viewModelScope.launch(Dispatchers.Default) {
            if (_listState.value is VideoListState.Success){
                _listState.value.data!!.forEachIndexed {index, item ->
                    retriever.setDataSource(item.stringUri)
                    val list = _listState.value.data!!.toMutableList()
                    list[index] = list[index].copy(duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull())
                    _listState.value = VideoListState.Success(list)
                }
            }
        }
    }

    private fun getListOfVideos(){
        getListOfVideosUseCase().onEach { result ->
            when(result){
                is Resource.Error -> _listState.value = VideoListState.Error(errorCode = result.errorCode, errorMessage = result.errorMessage)
                is Resource.Loading -> _listState.value = VideoListState.Loading
                is Resource.Success -> {
                    _listState.value = VideoListState.Success(data = result.data!!)
                    addMediaItems(_listState.value.data!!.map { it.mediaItem })
                    getVideosDuration()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateListOfVideos() {
        releaseMediaItems()
        getListOfVideos()
    }

    private fun setVideoData(position: Int){
        if (_listState.value is VideoListState.Success){
            _listState.value.data!![position].apply {
                _currentVideoDataState.value = VideoData(title = title, subtitle = subtitle, description = description)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}

sealed class VideoListState(val data: List<VideoEntity>? = null){
    class Success(data: List<VideoEntity>) : VideoListState(data)
    class Error(val errorCode: Int?, val errorMessage: String?) : VideoListState()
    data object Loading : VideoListState()
    data object Empty : VideoListState()
}