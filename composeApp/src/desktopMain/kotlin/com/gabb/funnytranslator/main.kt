package com.gabb.funnytranslator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.animateColorScheme
import com.materialkolor.rememberDynamicColorScheme
import java.awt.Cursor

fun main() = application {
    val windowState = rememberWindowState()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Sillyble",
        state = windowState,
        undecorated = true,
        transparent = true,
    ) {
        val translatorViewModel = viewModel { TranslatorViewModel("") }
        MaterialTheme(
            animateColorScheme(
                when (val translator = translatorViewModel.currentTranslator) {
                    null -> getColorScheme()
                    else -> rememberDynamicColorScheme(
                        primary = translator.getColor(),
                        isDark = isSystemInDarkTheme(),
                        isAmoled = false,
                        style = PaletteStyle.Fidelity
                    )
                }
            )
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant
                )
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    CustomTitleBar(
                        title = "Sillyble",
                        onMinimizeClick = { windowState.isMinimized = true },
                        onCloseClick = ::exitApplication
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    App()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrameWindowScope.CustomTitleBar(
    title: String,
    onMinimizeClick: () -> Unit,
    onCloseClick: () -> Unit,
) {
    WindowDraggableArea {
        TopAppBar(
            title = { Text(title) },
            actions = {
                IconButton(
                    onClick = onMinimizeClick,
                    modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                ) {
                    Icon(
                        Icons.Default.Minimize,
                        contentDescription = "Minimize",
                    )
                }

                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor(Cursor.HAND_CURSOR)))
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            )
        )
    }
}
