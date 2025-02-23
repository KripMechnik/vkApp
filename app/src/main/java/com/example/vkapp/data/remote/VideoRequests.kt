package com.example.vkapp.data.remote

import com.example.vkapp.common.Constants
import com.example.vkapp.common.Resource
import com.example.vkapp.data.remote.dto.VideoList
import com.example.vkapp.data.remote.network.BaseRequest
import io.ktor.http.HttpMethod
import javax.inject.Inject

class VideoRequests @Inject constructor(
    private val baseRequest: BaseRequest
){
    suspend fun getVideos() : Resource<VideoList> {
        return baseRequest(
            path = Constants.BASE_HOST,
            method = HttpMethod.Get
        )
    }
}