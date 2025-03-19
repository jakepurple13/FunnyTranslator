package com.gabb.funnytranslator.translators

data object GrootTranslator : Translator {
    override fun translate(text: String): String {
        return "I am Groot"
    }
}