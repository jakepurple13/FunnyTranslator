package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Translator that converts text into dog sounds.
 * Replaces each word with either "Woof" or "Bark" randomly.
 */
data object DogTranslator : Translator {

    override val lottiePath: String = "golden_retriever.json"

    /**
     * The possible dog sounds to use in translation
     */
    private val DOG_SOUNDS = listOf("Woof", "Bark")

    /**
     * Translates text into dog sounds
     *
     * @param text The text to translate
     * @return A string of dog sounds, one for each word in the input
     */
    override fun translate(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { DOG_SOUNDS.random() }
    }

    @Composable
    override fun getColor(): Color = Color(0xFF9D5100)

    @Composable
    override fun getIcon(): ImageVector = Icons.Default.Pets

    override fun toString(): String = "Dog"
}