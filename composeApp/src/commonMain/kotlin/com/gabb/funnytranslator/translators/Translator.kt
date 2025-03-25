package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

interface Translator {
    fun translate(text: String): String

    @Composable
    fun getColor(): Color

    @Composable
    fun getIcon(): ImageVector = Icons.Default.CatchingPokemon
}
