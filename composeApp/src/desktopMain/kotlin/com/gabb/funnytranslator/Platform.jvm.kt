package com.gabb.funnytranslator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Desktop implementation of the Platform interface.
 * Provides platform-specific information for the JVM platform.
 */
class JVMPlatform : Platform {
    override val name: String = "Desktop Java ${System.getProperty("java.version")}"
}

/**
 * Returns the platform-specific implementation for the JVM platform.
 *
 * @return A Platform instance for the JVM platform
 */
actual fun getPlatform(): Platform = JVMPlatform()

/**
 * Returns the color scheme to use for the UI based on the system theme.
 * Provides a detailed color scheme for both light and dark themes.
 *
 * @return A ColorScheme instance based on the system theme
 */
@Composable
actual fun getColorScheme(): ColorScheme {
    return if (isSystemInDarkTheme()) {
        darkColorScheme(
            primary = Color(0xFF90CAF9),           // Light Blue 200
            onPrimary = Color(0xFF000000),         // Black
            primaryContainer = Color(0xFF0D47A1),  // Blue 900
            onPrimaryContainer = Color(0xFFE3F2FD), // Blue 50
            secondary = Color(0xFF64B5F6),         // Blue 300
            onSecondary = Color(0xFF000000),       // Black
            secondaryContainer = Color(0xFF1565C0), // Blue 800
            onSecondaryContainer = Color(0xFFE3F2FD), // Blue 50
            tertiary = Color(0xFF42A5F5),          // Blue 400
            onTertiary = Color(0xFF000000),        // Black
            tertiaryContainer = Color(0xFF1976D2), // Blue 700
            onTertiaryContainer = Color(0xFFE3F2FD), // Blue 50
            background = Color(0xFF0A1929),        // Dark blue background
            onBackground = Color(0xFFE3F2FD),      // Blue 50
            surface = Color(0xFF0A1929),           // Dark blue surface
            onSurface = Color(0xFFE3F2FD),         // Blue 50
            surfaceVariant = Color(0xFF1A2C42),    // Slightly lighter blue
            onSurfaceVariant = Color(0xFFBBDEFB),  // Blue 100
            surfaceTint = Color(0xFF90CAF9),       // Light Blue 200
            inverseSurface = Color(0xFFE3F2FD),    // Blue 50
            inverseOnSurface = Color(0xFF0A1929),  // Dark blue
            error = Color(0xFFEF5350),             // Red 400
            onError = Color(0xFF000000),           // Black
            errorContainer = Color(0xFFB71C1C),    // Red 900
            onErrorContainer = Color(0xFFFFEBEE),  // Red 50
            outline = Color(0xFF64B5F6),           // Blue 300
            outlineVariant = Color(0xFF42A5F5),    // Blue 400
            scrim = Color(0xFF000000)              // Black
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF1976D2),           // Blue 700
            onPrimary = Color(0xFFFFFFFF),         // White
            primaryContainer = Color(0xFFBBDEFB),  // Blue 100
            onPrimaryContainer = Color(0xFF0D47A1), // Blue 900
            secondary = Color(0xFF1565C0),         // Blue 800
            onSecondary = Color(0xFFFFFFFF),       // White
            secondaryContainer = Color(0xFFE3F2FD), // Blue 50
            onSecondaryContainer = Color(0xFF0D47A1), // Blue 900
            tertiary = Color(0xFF2196F3),          // Blue 500
            onTertiary = Color(0xFFFFFFFF),        // White
            tertiaryContainer = Color(0xFFBBDEFB), // Blue 100
            onTertiaryContainer = Color(0xFF0D47A1), // Blue 900
            background = Color(0xFFE3F2FD),        // Blue 50
            onBackground = Color(0xFF0A1929),      // Dark blue
            surface = Color(0xFFE3F2FD),           // Blue 50
            onSurface = Color(0xFF0A1929),         // Dark blue
            surfaceVariant = Color(0xFFBBDEFB),    // Blue 100
            onSurfaceVariant = Color(0xFF0D47A1),  // Blue 900
            surfaceTint = Color(0xFF1976D2),       // Blue 700
            inverseSurface = Color(0xFF0A1929),    // Dark blue
            inverseOnSurface = Color(0xFFE3F2FD),  // Blue 50
            error = Color(0xFFB71C1C),             // Red 900
            onError = Color(0xFFFFFFFF),           // White
            errorContainer = Color(0xFFFFCDD2),    // Red 100
            onErrorContainer = Color(0xFFB71C1C),  // Red 900
            outline = Color(0xFF1976D2),           // Blue 700
            outlineVariant = Color(0xFF2196F3),    // Blue 500
            scrim = Color(0xFF000000)              // Black
        )
    }
}

/**
 * Provides a share button for the desktop platform.
 * Since desktop doesn't have a native share functionality like mobile platforms,
 * this implementation copies the text to the clipboard.
 *
 * @param translatedText A function that returns the text to be shared
 */
@Composable
actual fun ShareButton(
    translatedText: () -> String,
    enabled: Boolean,
    modifier: Modifier,
) {

}
