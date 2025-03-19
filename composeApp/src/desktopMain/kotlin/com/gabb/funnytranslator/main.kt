package com.gabb.funnytranslator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.gabb.funnytranslator.translators.Uwuify
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "FunnyTranslator",
    ) {
        DevelopmentEntryPoint {
            App()
        }
    }
}