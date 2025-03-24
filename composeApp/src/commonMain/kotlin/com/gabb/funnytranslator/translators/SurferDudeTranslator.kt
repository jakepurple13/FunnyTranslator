package com.gabb.funnytranslator.translators

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data object SurferDudeTranslator : Translator {

    private val wordReplacements = mapOf(
        "good" to "gnarly",
        "great" to "epic",
        "amazing" to "totally tubular",
        "yes" to "for sure, brah",
        "no" to "no way, dude",
        "hello" to "yo, what's up",
        "goodbye" to "catch ya later",
        "friend" to "bro",
        "very" to "hella",
        "really" to "seriously",
        "food" to "grub",
        "tired" to "wiped out",
        "excited" to "stoked",
        "happy" to "stoked",
        "sad" to "bummed",
        "money" to "cash",
        "car" to "ride",
        "house" to "pad",
        "party" to "rager",
        "cool" to "rad"
    )

    private val fillers = listOf(
        "dude",
        "bro",
        "man",
        "like",
        "totally",
        "seriously",
        "gnarly",
        "rad"
    )

    private val sentenceEnders = listOf(
        ", dude!",
        ", brah!",
        ", man!",
        "!",
        ", you know?",
        ", right?",
        ", stoked!",
        ", radical!"
    )

    private val sentenceStarters = listOf(
        "Yo, ",
        "Dude, ",
        "Check it out, ",
        "Bro, ",
        "Whoa, ",
        "Sick, "
    )

    override fun translate(text: String): String {
        if (text.isBlank()) return text

        // Split into sentences
        val sentences = text.split(Regex("[.!?]")).filter { it.isNotBlank() }
        if (sentences.isEmpty()) return surferify(text)

        // Process each sentence
        return sentences.joinToString(" ") { sentence ->
            surferify(sentence)
        }
    }

    private fun surferify(sentence: String): String {
        var result = sentence.trim()

        // Add sentence starter (40% chance)
        if (Random.nextInt(100) < 40) {
            result = sentenceStarters.random() + result.lowercase()
        }

        // Replace words
        for ((original, replacement) in wordReplacements) {
            result = result.replace(Regex("\\b$original\\b", RegexOption.IGNORE_CASE), replacement)
        }

        // Add fillers (60% chance)
        if (Random.nextInt(100) < 60) {
            val words = result.split(" ").toMutableList()
            val position = Random.nextInt(words.size)
            words.add(position, fillers.random())
            result = words.joinToString(" ")
        }

        // Add sentence ender
        result += sentenceEnders.random()

        return result
    }

    override fun toString(): String = "Surfer Dude Translator"

    @Composable
    override fun getColor(): Color {
        return Color(0xff31FFF5) // Deep Sky Blue, representing ocean
    }
}