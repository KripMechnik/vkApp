package com.example.vkapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val videos: List<Video>
)