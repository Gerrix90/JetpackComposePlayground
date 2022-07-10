package com.jetpackcompose.playground

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun FadeInFadeOutHeart() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var isVisible by remember {
            mutableStateOf(false)
        }

        Button(onClick = {
            isVisible = !isVisible
        }) {
            Text(text = if (isVisible) "Hide" else "Show")
        }

        Spacer(modifier = Modifier.size(150.dp))


        AnimatedVisibility(
            isVisible,
            exit = fadeOut(animationSpec = tween(2000, easing = LinearEasing)),
            enter = fadeIn(animationSpec = tween(2000, easing = LinearEasing))
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Red, shape = heart())
            )
        }

    }
}

@Composable
fun AnimatedContentSize() {
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
