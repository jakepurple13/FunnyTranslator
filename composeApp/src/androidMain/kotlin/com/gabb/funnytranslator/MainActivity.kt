package com.gabb.funnytranslator

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val initialText = intent.extras?.getString(Intent.EXTRA_TEXT) ?: ""
        setContent {
            App(initialText)
        }
    }
}

private fun Bundle.printBundle() {
    keySet().forEach {
        println(it + " | " + get(it))
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}