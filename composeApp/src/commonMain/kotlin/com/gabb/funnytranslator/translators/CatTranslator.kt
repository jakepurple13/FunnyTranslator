package com.gabb.funnytranslator.translators

import kotlin.random.Random

data object CatTranslator : Translator {
    override fun translate(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { if (Random.nextBoolean()) "Meow" else "Nya" }
    }

    override fun toString(): String = "Cat Translator"
}

data object DogTranslator : Translator {
    override fun translate(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { if (Random.nextBoolean()) "Woof" else "Bark" }
    }

    override fun toString(): String = "Dog Translator"
}