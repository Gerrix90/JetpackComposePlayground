package com.jetpackcompose.playground

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test


internal class MainActivityTest{

    @get:Rule()
    val composableRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testButton(){
        composableRule.onNodeWithText("Button").performClick()
    }

    @Test
    fun testTextField(){
        val someText = "This is text for testing"
        composableRule.onNodeWithTag("my_textField").performTextInput(someText)
        composableRule.onNodeWithTag("my_textField").assertTextContains(someText)
    }
}