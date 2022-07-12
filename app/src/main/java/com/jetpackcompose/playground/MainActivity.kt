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
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpackcompose.playground.TensorFLowHelper.imageSize


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        

        }
    }



}

@Composable
fun drawPathAnimation() {
    val ACTION_IDLE = 0
    val ACTION_DOWN = 1
    val ACTION_MOVE = 2


    val path = remember { Path() }
    var eventState by remember { mutableStateOf(ACTION_IDLE) }

    val drawModifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .background(Color.Yellow)
        .clipToBounds()


    var targetIndexValue by remember {
        mutableStateOf(0)
    }

    val currentIndex by animateIntAsState(
        targetValue = targetIndexValue,
        animationSpec = tween(7000, easing = LinearEasing)
    )

    val points = parsePoint()
    val pointsCopy = mutableListOf<Offset>()

    LaunchedEffect(Unit) {
        targetIndexValue = points.size - 1
    }


    eventState = ACTION_DOWN


    Canvas(modifier = drawModifier) {

        pointsCopy.add(points.get(currentIndex))

        when (eventState) {
            ACTION_DOWN -> {
                path.moveTo(pointsCopy.get(0).x, pointsCopy.get(0).y)
                eventState = ACTION_MOVE

            }
            ACTION_MOVE -> {
                path.lineTo(pointsCopy.get(currentIndex).x, pointsCopy.get(currentIndex).y)

            }

            else -> Unit
        }

        drawPath(
            color = Color.Red,
            path = path,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }

}

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
