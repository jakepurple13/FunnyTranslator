package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Interface for all translators in the application.
 * Each translator implements a unique way to transform input text.
 */
interface Translator {
    /**
     * Translates the input text according to the specific translator's rules.
     *
     * @param text The input text to translate
     * @return The translated text
     */
    fun translate(text: String): String

    /**
     * Returns the color associated with this translator.
     * Used for UI theming.
     *
     * @return The color for this translator
     */
    @Composable
    fun getColor(): Color

    /**
     * Returns the icon associated with this translator.
     * Used for UI representation.
     *
     * @return The icon for this translator
     */
    @Composable
    fun getIcon(): ImageVector = Icons.Default.CatchingPokemon

    val lottiePath: String? get() = null
}
