package com.jetpackcompose.playground.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _isSplashScreen = MutableStateFlow(true)
    val isSplashScreen = _isSplashScreen

    init {
        viewModelScope.launch {
            delay(2000)
            _isSplashScreen.value = false
        }
    }
}