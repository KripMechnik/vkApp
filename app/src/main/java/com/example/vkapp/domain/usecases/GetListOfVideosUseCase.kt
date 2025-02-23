package com.example.vkapp.domain.usecases

import com.example.vkapp.common.Resource
import com.example.vkapp.data.remote.dto.toVideoEntity
import com.example.vkapp.domain.entity.VideoEntity
import com.example.vkapp.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListOfVideosUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    operator fun invoke(): Flow<Resource<List<VideoEntity>>> = flow {
        emit(Resource.Loading())
        val response = videoRepository.getListOfVideos()
        if (response is Resource.Success) emit(Resource.Success(data = response.data!!.categories[0].videos.map { it.toVideoEntity() }))
        else emit(Resource.Error(errorMessage = response.errorMessage, errorCode = response.errorCode))
    }
}