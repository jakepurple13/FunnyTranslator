package com.gabb.funnytranslator

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import com.gabb.funnytranslator.translators.*

class TranslatorViewModel(
    initialText: String,
) : ViewModel() {
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
        ValleyGirlTranslator,
        YodaTranslator,
    )

    var currentTranslator by mutableStateOf<Translator?>(translatorList.random())

    val chosenTranslator by derivedStateOf {
        currentTranslator?.toString() ?: "No translator selected"
    }

    var text by mutableStateOf(initialText)

    val translatedText by derivedStateOf {
        val string = if (text.isBlank()) null else translate(text)
        string ?: "No translation"
    }

    fun translate(text: String): String? = currentTranslator?.translate(text)

    fun copyToClipboard(clipboard: ClipboardManager) {
        translatedText.let { clipboard.setText(AnnotatedString(it)) }
    }
}