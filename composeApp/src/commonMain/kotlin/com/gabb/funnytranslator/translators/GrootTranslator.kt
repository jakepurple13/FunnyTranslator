package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Park
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data object GrootTranslator : Translator {

    private val iAmGrootWords = arrayOf("I", "am", "Groot")

    override fun translate(text: String): String {
        val splitText = text.split(" ")
        return if (splitText.size > 3)
            "I am Groot"
        else
            splitText
                .mapIndexed { index, s -> if (s.isNotEmpty()) iAmGrootWords[index % iAmGrootWords.size] else "" }
                .joinToString(" ")
    }

    override fun toString(): String = "Groot Translator"

    @Composable
    override fun getColor(): Color {
        return Color(0xff397E00)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.Park
    }
}
