package com.gabb.funnytranslator

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun getColorScheme(): ColorScheme

@Composable
expect fun ShareButton(
    translatedText: () -> String,
    enabled: Boolean,
    modifier: Modifier,
)

expect fun Modifier.imeNestedPadding(): Modifier