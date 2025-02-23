package com.example.vkapp.di

import android.app.Application
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.vkapp.data.remote.VideoRequests
import com.example.vkapp.data.remote.network.BaseRequest
import com.example.vkapp.data.repository.VideoRepositoryImpl
import com.example.vkapp.domain.repository.VideoRepository
import com.example.vkapp.domain.usecases.GetListOfVideosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesBaseClient(json: Json) = HttpClient(OkHttp){
        install(Logging){
            level = LogLevel.ALL
        }
        install(ContentNegotiation){
            json(json)
        }
        install(HttpTimeout){
            connectTimeoutMillis = 10_000L
            requestTimeoutMillis = 10_000L
            socketTimeoutMillis = 10_000L
        }

    }

    @Provides
    @Singleton
    fun providesJson(): Json = Json{
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesBaseRequest(baseClient: HttpClient, json: Json) = BaseRequest(baseClient, json)

    @Provides
    @Singleton
    fun providesVideoRequests(baseRequest: BaseRequest) = VideoRequests(baseRequest)

    @Provides
    @Singleton
    fun providesVideoRepository(videoRequests: VideoRequests): VideoRepository = VideoRepositoryImpl(videoRequests)

    @Provides
    @Singleton
    fun providesGetListOfVideosUseCase(videoRepository: VideoRepository) = GetListOfVideosUseCase(videoRepository)

    @Provides
    @Singleton
    fun provideVideoPlayer(app: Application): Player {
        return ExoPlayer.Builder(app)
            .build()
    }
}