package com.gabb.funnytranslator.translators

import kotlin.random.Random

data object ValleyGirlTranslator : Translator {

    private val wordReplacements = mapOf(
        "good" to "totally awesome",
        "bad" to "like, so lame",
        "great" to "amazing",
        "happy" to "stoked",
        "sad" to "bummed",
        "angry" to "totally freaking out",
        "yes" to "for sure",
        "no" to "as if",
        "hello" to "hey there",
        "goodbye" to "later",
        "friend" to "bestie",
        "very" to "so",
        "really" to "literally",
        "amazing" to "mind-blowing",
        "nice" to "cute"
    )

    private val fillers = listOf(
        "like",
        "um",
        "totally",
        "literally",
        "seriously",
        "I mean",
        "you know"
    )

    private val sentenceEnders = listOf(
        ", right?",
        ", you know?",
        "?",
        "!",
        ", duh!",
        ", totally!",
        ", I can't even!"
    )

    private val sentenceStarters = listOf(
        "Oh my god, ",
        "Like, ",
        "So, ",
        "Okay so, ",
        "Whatever, ",
        "I'm like, "
    )

    override fun translate(text: String): String {
        if (text.isBlank()) return text

        // Split into sentences
        val sentences = text.split(Regex("[.!?]")).filter { it.isNotBlank() }
        if (sentences.isEmpty()) return valleyGirlify(text)

        // Process each sentence
        return sentences.joinToString(" ") { sentence ->
            valleyGirlify(sentence)
        }
    }
    
    private fun valleyGirlify(sentence: String): String {
        var result = sentence.trim()

        // Add sentence starter (30% chance)
        if (Random.nextInt(100) < 30) {
            result = sentenceStarters.random() + result.lowercase()
        }

        // Replace words
        for ((original, replacement) in wordReplacements) {
            result = result.replace(Regex("\\b$original\\b", RegexOption.IGNORE_CASE), replacement)
        }

        // Add fillers (50% chance)
        if (Random.nextInt(100) < 50) {
            val words = result.split(" ").toMutableList()
            val position = Random.nextInt(words.size)
            words.add(position, fillers.random())
            result = words.joinToString(" ")
        }

        // Add sentence ender
        result += sentenceEnders.random()

        return result
    }

    override fun toString(): String = "Valley Girl Translator"
}