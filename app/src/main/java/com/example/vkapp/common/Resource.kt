package com.example.vkapp.common

sealed class Resource<T>(val data: T? = null, val errorMessage: String? = null, val errorCode: Int? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(errorMessage: String?, data: T? = null, errorCode: Int? = null) : Resource<T>(data, errorMessage, errorCode)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}