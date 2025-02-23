package com.example.vkapp.data.repository

import com.example.vkapp.common.Resource
import com.example.vkapp.data.remote.VideoRequests
import com.example.vkapp.data.remote.dto.Category
import com.example.vkapp.data.remote.dto.VideoList
import com.example.vkapp.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoRequests: VideoRequests
) : VideoRepository {
    override suspend fun getListOfVideos(): Resource<VideoList> {
        return videoRequests.getVideos()
    }
}