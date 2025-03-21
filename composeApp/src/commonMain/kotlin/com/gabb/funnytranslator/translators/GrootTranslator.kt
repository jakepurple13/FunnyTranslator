package com.gabb.funnytranslator.translators

data object GrootTranslator : Translator {

    private val iAmGrootWords = arrayOf("I", "am", "Groot")

    override fun translate(text: String): String {
        val splitText = text.split(" ")
        return if (splitText.size > 3)
            "I am Groot"
        else
            splitText
                .mapIndexed { index, s -> if (s.isNotEmpty()) iAmGrootWords[index % iAmGrootWords.size] else "" }
                .joinToString(" ")
    }

    override fun toString(): String = "Groot Translator"
}