package com.example.js_incorporation.ui.screens

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.js_incorporation.ui.utils.obtenerWindowSizeClass

@Composable
fun HomeScreen2() {
    val windowSizeClass: WindowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            HomeScreenCompacta()
        }

        WindowWidthSizeClass.Medium -> {
            HomeScreenMediana()
        }

        WindowWidthSizeClass.Expanded -> {
            HomeScreenExpandida()
        }

        else -> {
            HomeScreenCompacta()
        }
    }
}

