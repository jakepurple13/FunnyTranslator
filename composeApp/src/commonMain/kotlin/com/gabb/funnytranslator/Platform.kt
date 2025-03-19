package com.gabb.funnytranslator

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun getColorScheme(): ColorScheme

@Composable
expect fun ShareButton(translatedText: () -> String)