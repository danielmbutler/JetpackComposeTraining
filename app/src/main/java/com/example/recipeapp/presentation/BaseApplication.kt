package com.example.recipeapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    //should be saved in datastore
    val isDark = mutableStateOf(false)

    fun toggleLightTheme(){
        isDark.value =  !isDark.value
    }

}