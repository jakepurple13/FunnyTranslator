package com.gabb.funnytranslator

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
actual fun getColorScheme(): ColorScheme {
    return if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
}

@Composable
actual fun ShareButton(translatedText: () -> String) {
    IconButton(
        onClick = {
            runCatching {
                val currentViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
                val activityViewController = UIActivityViewController(listOf(translatedText()), null)
                currentViewController?.presentViewController(
                    viewControllerToPresent = activityViewController,
                    animated = true,
                    completion = null
                )
            }
        }
    ) { Icon(Icons.Default.Share, null) }
}