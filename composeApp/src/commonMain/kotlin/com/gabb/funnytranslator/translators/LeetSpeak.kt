package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data object LeetSpeak : Translator {
    override fun translate(text: String): String {
        return translateToLeet(text)
    }

    fun translateToLeet(text: String): String {
        val leetMap = mapOf(
            'a' to '4', 'b' to '8', 'e' to '3', 'i' to '1',
            'o' to '0', 's' to '5', 't' to '7', 'l' to '1'
        )
        return text.lowercase().map { leetMap[it] ?: it }.joinToString("")
    }

    fun translateFromLeet(text: String): String {
        val reverseLeetMap = mapOf(
            '4' to 'a', '8' to 'b', '3' to 'e', '1' to 'i',
            '0' to 'o', '5' to 's', '7' to 't'
        )
        return text.lowercase().map { reverseLeetMap[it] ?: it }.joinToString("")
    }

    override fun toString(): String = "LeetSpeak Translator"

    @Composable
    override fun getColor(): Color {
        return Color(0xff73FF00)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.Code
    }
}
