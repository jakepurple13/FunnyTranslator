package com.gabb.funnytranslator.translators

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface Translator {
    fun translate(text: String): String

    @Composable
    fun getColor(): Color
}