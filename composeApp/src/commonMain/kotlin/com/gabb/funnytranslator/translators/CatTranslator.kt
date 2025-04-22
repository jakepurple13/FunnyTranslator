package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Translator that converts text into cat sounds.
 * Replaces each word with either "Meow" or "Nya" randomly.
 */
data object CatTranslator : Translator {

    override val lottiePath: String = "cat.json"

    /**
     * The possible cat sounds to use in translation
     */
    private val CAT_SOUNDS = listOf("Meow", "Nya")

    /**
     * Translates text into cat sounds
     *
     * @param text The text to translate
     * @return A string of cat sounds, one for each word in the input
     */
    override fun translate(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { CAT_SOUNDS.random() }
    }

    @Composable
    override fun getColor(): Color = Color(0xFFFF9D00)

    @Composable
    override fun getIcon(): ImageVector = Icons.Default.Pets

    override fun toString(): String = "Cat"
}
