package com.gabb.funnytranslator

import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext

/**
 * Android implementation of the Platform interface.
 * Provides platform-specific information for the Android platform.
 */
class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

/**
 * Returns the platform-specific implementation for the Android platform.
 *
 * @return A Platform instance for the Android platform
 */
actual fun getPlatform(): Platform = AndroidPlatform()

/**
 * Returns the color scheme to use for the UI based on the system theme.
 * Uses dynamic color scheme on Android S and above, falling back to
 * standard Material color schemes on older versions.
 *
 * @return A ColorScheme instance based on the system theme and Android version
 */
@Composable
actual fun getColorScheme(): ColorScheme {
    val darkTheme = isSystemInDarkTheme()
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
}

/**
 * Provides a share button for the Android platform.
 * Uses Android's native sharing functionality via Intent.ACTION_SEND.
 *
 * @param translatedText A function that returns the text to be shared
 */
@Composable
actual fun ShareButton(
    translatedText: () -> String,
    enabled: Boolean,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val shareItem = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    /*FilledTonalIconButton(
        onClick = {
            runCatching {
                val text = translatedText()
                if (text.isNotBlank()) {
                    shareItem.launch(
                        Intent.createChooser(
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            },
                            "Share translated text to..."
                        )
                    )
                }
            }
        },
        shape = MaterialTheme.shapes.medium,
    ) { Icon(Icons.Default.Share, contentDescription = "Share translation") }*/

    Card(
        onClick = {
            runCatching {
                val text = translatedText()
                if (text.isNotBlank()) {
                    shareItem.launch(
                        Intent.createChooser(
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            },
                            "Share translated text to..."
                        )
                    )
                }
            }
        },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
                .copy(alpha = 0.38f)
                .compositeOver(MaterialTheme.colorScheme.surface),
        ),
        enabled = enabled,
        modifier = modifier
    ) {
        Icon(
            Icons.Default.Share,
            contentDescription = "Share translation",
            modifier = Modifier
                .padding(AppConstants.DEFAULT_PADDING)
                .align(Alignment.CenterHorizontally)
        )
    }
}
