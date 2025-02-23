package com.example.vkapp.domain.repository

import com.example.vkapp.common.Resource
import com.example.vkapp.data.remote.dto.Category
import com.example.vkapp.data.remote.dto.VideoList

interface VideoRepository {
    suspend fun getListOfVideos(): Resource<VideoList>
}