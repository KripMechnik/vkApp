package com.example.vkapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VideoList(
    val categories: List<Category>
)