package com.example.vkapp.data.remote.network

import android.util.Log
import com.example.vkapp.common.Constants
import com.example.vkapp.common.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.headers
import io.ktor.client.request.host
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import io.ktor.util.StringValues
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Named


class BaseRequest @Inject constructor(
    val baseClient: HttpClient,
    val json: Json
){

    suspend inline operator fun <reified T> invoke(
        path: String,
        params: StringValues = StringValues.Empty,
        method: HttpMethod,
        body: Any? = null,
        headers: StringValues = StringValues.Empty
    ) = try {
        val response = baseClient.request {
            this.method = method
            this.host = path
            url {
                protocol = URLProtocol.HTTPS
                parameters.appendAll(params)
                headers {
                    appendAll(headers)
                }
            }
//            headers{
//                append("Connection", "close")
//            }
            contentType(ContentType.Application.Json)
            body?.let {
                setBody(it)
            }
        }

        if (response.status.isSuccess()){
            val stringBody = response.body<String>()
            Log.e("body", stringBody)
            if (stringBody.startsWith("{") || stringBody.startsWith("[")) Resource.Success(data = json.decodeFromString<T>(stringBody))
            else Resource.Success()
        } else {
            val stringBody = response.body<String>()
            Log.e("refresh", stringBody)
            Resource.Error(
                errorCode = response.status.value,
                errorMessage = response.status.description + "\n" + stringBody
            )
        }

    } catch (e: SerializationException){
        e.printStackTrace()
        Resource.Error(
            errorCode = -1,
            errorMessage = "Serialization exception"
        )
    } catch (e: ServerResponseException){
        e.printStackTrace()
        Resource.Error(
            errorCode = 500,
            errorMessage = "Server error"
        )
    } catch (e: UnknownHostException){
        e.printStackTrace()
        Resource.Error(
            errorCode = -2,
            errorMessage = "No internet connection"
        )
    } catch (e: IOException){
        e.printStackTrace()
        Resource.Error(
            errorCode = -3,
            errorMessage = "IOException"
        )
    } catch (e: Exception){
        e.printStackTrace()
        Resource.Error(
            errorCode = -4,
            errorMessage = "Unknown error"
        )
    }

}