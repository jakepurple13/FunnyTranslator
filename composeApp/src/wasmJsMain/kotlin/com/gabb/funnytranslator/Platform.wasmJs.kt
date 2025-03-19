package com.gabb.funnytranslator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

@Composable
actual fun getColorScheme(): ColorScheme {
    return if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
}

@Composable
actual fun ShareButton(translatedText: () -> String) {

}