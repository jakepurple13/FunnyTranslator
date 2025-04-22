package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups3
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

    override fun toString(): String = "Minionese"

    @Composable
    override fun getColor(): Color {
        return Color(0xffFFEE00)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.Groups3
    }
}
