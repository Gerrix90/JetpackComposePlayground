package com.jetpackcompose.playground

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.jetpackcompose.playground.TensorFLowHelper.imageSize
import com.jetpackcompose.playground.ui.theme.JetPackComposePlaygroundTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.File


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            JetPackComposePlaygroundTheme {

            }

        }
    }



//    @Composable
//    private fun captureContentOfComposable() {
//        val context = LocalContext.current
//
//        val screenshotState = rememberScreenshotState()
//
//        var progress by remember { mutableStateOf(0f) }
//
//        ScreenshotBox(screenshotState = screenshotState) {
//            Column(
//                modifier = Modifier
//                    .border(2.dp, Color.Green)
//                    .padding(5.dp)
//            ) {
//                drawPointsAnimation(
//                    modifier = Modifier
//                        .background(Color.Black)
//                        .fillMaxWidth()
//                        // This is for displaying different ratio, optional
//                        .aspectRatio(4f / 3)
//                )
//
//    //                    Text(text = "Counter: $counter")
//                Slider(value = progress, onValueChange = { progress = it })
//            }
//        }
//
//        Button(onClick = {
//            screenshotState.capture()
//            screenshotState.callback?.let {
//                it()?.let { it1 ->
//                    File(context.filesDir, "screenshot.png")
//                        .writeBitmap(it1, Bitmap.CompressFormat.PNG, 85)
//                }
//            }
//
//        }) {
//            Text(text = "Take Screenshot")
//        }
//    }
}

//private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
//    outputStream().use { out ->
//        bitmap.compress(format, quality, out)
//        out.flush()
//    }
//}
//
///**
// * Create a State of screenshot of composable that is used with that is kept on each recomposition.
// * @param delayInMillis delay before each screenshot if [liveScreenshotFlow] is collected.
// */
//@Composable
//fun rememberScreenshotState(delayInMillis: Long = 20) = remember {
//    ScreenshotState(delayInMillis)
//}
//
///**
// * State of screenshot of composable that is used with.
// * @param timeInMillis delay before each screenshot if [liveScreenshotFlow] is collected.
// */
//class ScreenshotState internal constructor(
//    private val timeInMillis: Long = 20
//) {
//    internal var callback: (() -> Bitmap?)? = null
//
//    private val bitmapState = mutableStateOf(callback?.invoke())
//
//    /**
//     * Captures current state of Composables inside [ScreenshotBox]
//     */
//    fun capture() {
//        bitmapState.value = callback?.invoke()
//    }
//
//    val liveScreenshotFlow = flow {
//        while (true) {
//            val bmp = callback?.invoke()
//            bmp?.let {
//                emit(it)
//            }
//            delay(timeInMillis)
//        }
//    }
//
//    val bitmap: Bitmap?
//        get() = bitmapState.value
//
//    val imageBitmap: ImageBitmap?
//        get() = bitmap?.asImageBitmap()
//}
//
///**
// * A composable that gets screenshot of Composable that is in [content].
// * @param screenshotState state of screenshot that contains [Bitmap].
// * @param content Composable that will be captured to bitmap on action or periodically.
// */
//@Composable
//fun ScreenshotBox(
//    modifier: Modifier = Modifier,
//    screenshotState: ScreenshotState,
//    content: @Composable () -> Unit,
//) {
//    val view = LocalView.current
//
//    var composableBounds by remember {
//        mutableStateOf<Rect?>(null)
//    }
//
//    DisposableEffect(Unit) {
//
//        var bitmap: Bitmap? = null
//
//        screenshotState.callback = {
//            composableBounds?.let { bounds ->
//
//                if (bounds.width == 0f || bounds.height == 0f) return@let
//
//                bitmap = Bitmap.createBitmap(
//                    bounds.width.toInt(),
//                    bounds.height.toInt(),
//                    Bitmap.Config.ARGB_8888
//                )
//
//                bitmap?.let { bmp ->
//
//                    val canvas = Canvas(bmp)
//                        .apply {
//                            translate(-bounds.left, -bounds.top)
//                        }
//                    view.draw(canvas)
//                }
//            }
//            bitmap
//        }
//
//        onDispose {
//            bitmap?.apply {
//                if (!isRecycled) {
//                    recycle()
//                    bitmap = null
//                }
//            }
//            screenshotState.callback = null
//        }
//    }
//
//    Box(modifier = modifier
//        .onGloballyPositioned {
//            composableBounds = it.boundsInRoot()
//        }
//    ) {
//        content()
//    }
//}


@Composable
fun ImagePicker() {
    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            photoUri = it
        }
    )

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            photoUri?.let {
                if (Build.VERSION.SDK_INT < 28)
                    bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap = ImageDecoder.decodeBitmap(
                        source,
                        ImageDecoder.OnHeaderDecodedListener { decoder, info, source ->
                            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                            decoder.isMutableRequired = true
                        })
                }
            }

            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Image from the gallery",
                    Modifier.size(400.dp)
                )
                Spacer(modifier = Modifier.padding(20.dp))

                val scaledBitmap = Bitmap.createScaledBitmap(it, imageSize, imageSize, false);
                TensorFLowHelper.classifyImage(scaledBitmap) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = "Image is classified as:")
                        Text(text = it, color = Color.White, fontSize = 24.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Button(onClick = {
                launcher.launch("image/*")
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Pick an image")
            }
        }
    }
}
