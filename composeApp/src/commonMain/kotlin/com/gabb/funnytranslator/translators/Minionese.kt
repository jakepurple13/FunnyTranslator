package com.gabb.funnytranslator.translators

import funnytranslator.composeapp.generated.resources.Res
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class, DelicateCoroutinesApi::class)
data object Minionese : Translator {

    private val words = mutableMapOf<String, String>()

    init {
        GlobalScope.launch {
            runCatching {
                words.putAll(
                    Json.decodeFromString<MutableMap<String, String>>(
                        Res.readBytes("files/minionese.json").decodeToString()
                    )
                )
            }
        }
    }

    override fun translate(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { word ->
                words[word]
                    ?: words[word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }]
                    ?: words[word.lowercase()]
                    ?: words[word.uppercase()]
                    ?: word
            }
    }

    override fun toString(): String = "Minionese Translator"
}
