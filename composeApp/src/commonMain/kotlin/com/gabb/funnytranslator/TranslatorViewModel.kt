package com.gabb.funnytranslator

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gabb.funnytranslator.translators.*

/**
 * ViewModel that manages the state and logic for the translator application.
 * Handles the selection of translators and the translation of text.
 *
 * @property initialText The initial text to be translated
 */
class TranslatorViewModel(
    initialText: String,
) : ViewModel() {
    /**
     * List of all available translators in the application.
     */
    val translatorList = listOf(
        Uwuify,
        Pirate,
        LeetSpeak,
        GrootTranslator,
        MorseCode,
        CatTranslator,
        DogTranslator,
        Minionese,
        ShakespeareTranslator,
        SurferDudeTranslator,
        ValleyGirlTranslator,
        YodaTranslator,
        ZombieTranslator,
        HackerTranslator,
    )

    /**
     * The currently selected translator.
     * Initialized with a random translator from the list.
     */
    var currentTranslator by mutableStateOf<Translator?>(translatorList.random())

    /**
     * The display name of the currently selected translator.
     * Derived from the currentTranslator.
     */
    val chosenTranslator by derivedStateOf {
        currentTranslator?.toString() ?: "No translator selected"
    }

    /**
     * The input text to be translated.
     * Initialized with the provided initialText.
     */
    var text by mutableStateOf(initialText)

    /**
     * The translated text based on the current input and selected translator.
     * Updates automatically when text or currentTranslator changes.
     */
    val translatedText by derivedStateOf {
        if (text.isBlank()) {
            "No translation"
        } else {
            try {
                translate(text) ?: "No translation"
            } catch (e: Exception) {
                "Translation error: ${e.message}"
            }
        }
    }

    /**
     * Translates the given text using the current translator.
     *
     * @param text The text to translate
     * @return The translated text, or null if no translator is selected
     */
    fun translate(text: String): String? = currentTranslator?.translate(text)
}
