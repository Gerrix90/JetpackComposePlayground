package com.jetpackcompose.playground

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun alertDialog() {
    var isOpen by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current


    if (isOpen)
        AlertDialog(onDismissRequest = {
            isOpen = false
            Toast.makeText(context, "Dismissed", Toast.LENGTH_SHORT).show()
        }, title = {
            Text(text = "Title of dialog")
        }, text = {
            Text(text = "This is a description of dialog")
        }, dismissButton = {
            TextButton(onClick = {
                isOpen = false
                Toast.makeText(context, "Dismissed", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Dismiss")
            }
        }, confirmButton = {
            TextButton(onClick = {
                isOpen = false
                Toast.makeText(context, "Confirmed", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "Confirm")
            }
        }, backgroundColor = Color.White)


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { isOpen = true }) {
            Text(text = "Show dialog")
        }
    }
}