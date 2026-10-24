package com.example.js_incorporation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.js_incorporation.ui.screens.HomeScreen2
import com.example.js_incorporation.ui.theme.JS_INCORPORATIONTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JS_INCORPORATIONTheme {
                HomeScreen2()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JS_INCORPORATIONTheme {
        HomeScreen2()
    }
}