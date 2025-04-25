package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

data object YodaTranslator : Translator {

    override val lottiePath: String = "galaxy.json"

    private val yodaPhrases = listOf(
        "Hmm, ",
        "Yes, yes. ",
        "Powerful you have become, ",
        "The Force is strong with this one. ",
        "Much to learn, you still have. "
    )

    private val yodaEndings = listOf(
        ", hmm?",
        ", yes.",
        ", I sense.",
        ", I do.",
        "."
    )

    override fun translate(text: String): String {
        if (text.isBlank()) return text

        // Split into sentences
        val sentences = text.split(Regex("[.!?]")).filter { it.isNotBlank() }
        if (sentences.isEmpty()) return yodify(text)

        // Process each sentence
        return sentences.joinToString(" ") { sentence ->
            yodify(sentence)
        }
    }

    private fun yodify(sentence: String): String {
        val trimmed = sentence.trim()
        if (trimmed.count { it.isWhitespace() } < 2) {
            // Too short to rearrange meaningfully
            return trimmed + yodaEndings.random()
        }

        // Split into words
        val words = trimmed.split(Regex("\\s+"))

        // Yoda often puts the object or predicate at the beginning
        // and the subject at the end
        val result = if (words.size >= 3 && Random.nextInt(100) < 80) {
            // Take the last third of words and move them to the front
            val pivotPoint = (words.size * 2 / 3).coerceAtLeast(1)
            val lastPart = words.subList(pivotPoint, words.size)
            val firstPart = words.subList(0, pivotPoint)

            // Join with a comma
            lastPart.joinToString(" ") + ", " + firstPart.joinToString(" ")
        } else {
            // Sometimes just use the original order
            trimmed
        }

        // Add Yoda-like phrases at the beginning (30% chance)
        val withPhrase = if (Random.nextInt(100) < 30) {
            yodaPhrases.random() + result.lowercase()
        } else {
            result
        }

        // Add Yoda-like endings
        return withPhrase + yodaEndings.random()
    }

    override fun toString(): String = "Yoda"

    @Composable
    override fun getColor(): Color {
        return Color(0xffBAE997)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.RocketLaunch
    }
}
