package com.example.vkapp.ui.video_hosting.video.view

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.vkapp.ui.video_hosting.video.VideoScreenPresenter


@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(
    presenter: VideoScreenPresenter
) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    BackHandler {
        presenter.navigateBack()
    }

    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current

    val currentVideoData by presenter.videoData.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event -> lifecycle = event }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also { view ->
                    view.player = presenter.player
                    if (context.resources.configuration.orientation == 2) {
                        view.setFullscreenButtonState(true)
                        context.setScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, view)
                    }
                    view.setFullscreenButtonClickListener { isFullScreen ->
                        with(context){
                            if (isFullScreen) {
                                setScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, view)
                            } else {
                                setScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, view)
                            }
                        }
                    }

                }
            },
            update = {
                when(lifecycle){
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                    }
                    else -> Unit
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (context.resources.configuration.orientation == 1){
                        Modifier
                            .requiredHeightIn(max = 300.dp)
                    } else {
                        Modifier
                    }
                )

        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            text = currentVideoData.title,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            text = currentVideoData.subtitle,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            text = currentVideoData.description,
            style = MaterialTheme.typography.titleSmall
        )
    }



}

fun Context.findActivity(): Activity? = when (this) {
    is Activity       -> this
    is ContextWrapper -> baseContext.findActivity()
    else              -> null
}

@OptIn(UnstableApi::class)
fun Context.setScreenOrientation(orientation: Int, view: PlayerView) {
    val activity = this.findActivity() ?: return
    activity.requestedOrientation = orientation
    if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        hideSystemUi()
    } else {
        view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        showSystemUi()
    }
}

fun Context.hideSystemUi() {
    val activity = this.findActivity() ?: return
    val window = activity.window ?: return
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, window.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Context.showSystemUi() {
    val activity = this.findActivity() ?: return
    val window = activity.window ?: return
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(
        window,
        window.decorView
    ).show(WindowInsetsCompat.Type.navigationBars())
}