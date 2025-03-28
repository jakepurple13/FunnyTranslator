package com.gabb.funnytranslator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * WebAssembly implementation of the Platform interface.
 * Provides platform-specific information for the Web with Kotlin/Wasm platform.
 */
class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

/**
 * Returns the platform-specific implementation for the WebAssembly platform.
 *
 * @return A Platform instance for the WebAssembly platform
 */
actual fun getPlatform(): Platform = WasmPlatform()

/**
 * Returns the color scheme to use for the UI based on the system theme.
 * Provides a standard Material color scheme for both light and dark themes.
 *
 * @return A ColorScheme instance based on the system theme
 */
@Composable
actual fun getColorScheme(): ColorScheme {
    return if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
}

/**
 * Provides a share button for the WebAssembly platform.
 * Currently a placeholder for future implementation of sharing functionality.
 *
 * @param translatedText A function that returns the text to be shared
 */
@Composable
actual fun ShareButton(translatedText: () -> String) {

}
