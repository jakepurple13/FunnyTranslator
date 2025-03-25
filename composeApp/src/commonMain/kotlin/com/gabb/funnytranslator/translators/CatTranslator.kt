package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

data object CatTranslator : Translator {
    override fun translate(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { if (Random.nextBoolean()) "Meow" else "Nya" }
    }

    @Composable
    override fun getColor(): Color {
        return Color(0xFFFF9D00)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.Pets
    }

    override fun toString(): String = "Cat Translator"
}

data object DogTranslator : Translator {
    override fun translate(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { if (Random.nextBoolean()) "Woof" else "Bark" }
    }

    @Composable
    override fun getColor(): Color {
        return Color(0xFF9D5100)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.Pets
    }

    override fun toString(): String = "Dog Translator"
}
