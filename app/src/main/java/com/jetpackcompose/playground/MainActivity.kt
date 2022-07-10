package com.jetpackcompose.playground

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpackcompose.playground.TensorFLowHelper.imageSize
import com.jetpackcompose.playground.ui.theme.JetPackComposePlaygroundTheme
import com.jetpackcompose.playground.ui.theme.Shapes


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposePlaygroundTheme {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Color.Gray
                            )
                            .animateContentSize()
                    ) {

                        var isExpended by remember {
                            mutableStateOf(false)
                        }

                        Row(modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                isExpended = !isExpended
                            }) {

                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "",
                                tint = Color.White
                            )
                            Text(
                                text = if (isExpended) "Hide more info" else "Click for more information...",
                                color = Color.White,
                                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
                            )


                        }

                        if (isExpended)
                            Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        isExpended = !isExpended

                                    })

                    }


                }

            }
//                ImagePicker()
        }
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
