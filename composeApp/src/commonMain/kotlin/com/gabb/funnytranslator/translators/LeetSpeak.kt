package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Translator that converts text into "leet speak" (1337 5p34k).
 * Replaces certain letters with numbers or symbols that resemble them.
 */
data object LeetSpeak : Translator {

    override val lottiePath: String = "html.json"

    /**
     * Map of characters to their leet speak equivalents
     */
    private val LEET_MAP = mapOf(
        'a' to '4', 'b' to '8', 'e' to '3', 'i' to '1',
        'o' to '0', 's' to '5', 't' to '7', 'l' to '1'
    )

    /**
     * Translates normal text to leet speak
     *
     * @param text The text to translate
     * @return The text converted to leet speak
     */
    override fun translate(text: String): String {
        return text.lowercase().map { LEET_MAP[it] ?: it }.joinToString("")
    }

    override fun toString(): String = "LeetSpeak"

    @Composable
    override fun getColor(): Color = Color(0xff73FF00)

    @Composable
    override fun getIcon(): ImageVector = Icons.Default.Code
}
