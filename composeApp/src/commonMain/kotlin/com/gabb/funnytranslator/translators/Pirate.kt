package com.gabb.funnytranslator.translators

data object Pirate : Translator {
    val englishToPirate = mapOf(
        "hello" to "ahoy",
        "my" to "me",
        "name" to "name be",
        "is" to "be",
        "friend" to "matey",
        "yes" to "aye",
        "no" to "nay",
        "treasure" to "booty",
        "goodbye" to "farewell",
        "," to ",",
        "." to "."
    )

    override fun translate(text: String): String {
        return translateToPirate(text)
    }

    fun translateToPirate(sentence: String): String {
        val words = sentence.lowercase().split(" ")
        val translatedWords = words.map { word ->
            englishToPirate[word] ?: word
        }
        return translatedWords.joinToString(" ")
    }
}