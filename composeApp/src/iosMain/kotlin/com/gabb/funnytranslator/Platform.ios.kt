package com.gabb.funnytranslator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

/**
 * iOS implementation of the Platform interface.
 * Provides platform-specific information for the iOS platform.
 */
class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

/**
 * Returns the platform-specific implementation for the iOS platform.
 *
 * @return A Platform instance for the iOS platform
 */
actual fun getPlatform(): Platform = IOSPlatform()

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
 * Provides a share button for the iOS platform.
 * Uses iOS's native sharing functionality via UIActivityViewController.
 *
 * @param translatedText A function that returns the text to be shared
 */
@Composable
actual fun ShareButton(translatedText: () -> String) {
    IconButton(
        onClick = {
            runCatching {
                val text = translatedText()
                if (text.isNotBlank()) {
                    val currentViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
                    val activityViewController = UIActivityViewController(listOf(text), null)
                    currentViewController?.presentViewController(
                        viewControllerToPresent = activityViewController,
                        animated = true,
                        completion = null
                    )
                }
            }
        }
    ) { Icon(Icons.Default.Share, contentDescription = "Share translation") }
}
