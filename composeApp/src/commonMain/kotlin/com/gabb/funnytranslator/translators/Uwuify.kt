package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

/**
 * Translator that converts text into "uwu" speak, a cute and playful internet language.
 * Features include word replacements, character substitutions, stuttering, and emoji additions.
 */
data object Uwuify : Translator {

    override val lottiePath: String = "magic_wand.json"

    // Chance constants
    private const val STUTTER_CHANCE = 20
    private const val EMOJI_CHANCE = 50

    /**
     * Word replacements for uwu-ification
     */
    private val WORDS = mapOf(
        "small" to "smol",
        "cute" to "kawaii~",
        "fluff" to "floof",
        "love" to "luv",
        "stupid" to "baka",
        "what" to "nani",
        "meow" to "nya~",
    )

    /**
     * Emojis to add to the text
     */
    private val EMOJIS = listOf(
        " rawr x3", " OwO", " UwU", " o.O", " -.-", " >w<",
        " (⑅˘꒳˘)", " (ꈍᴗꈍ)", " (˘ω˘)", " (U ᵕ U❁)",
        " σωσ", " òωó", " (U ﹏ U)", " ʘwʘ", " :3",
        " XD", " nyaa~~", " mya", " >_<", " rawr",
        " ^^", " (^•ω•^)", " (✿oωo)", " („ᵕᴗᵕ„)", " (。U⁄ ⁄ω⁄ ⁄ U。)"
    )

    /**
     * Punctuation characters that can be followed by emojis
     */
    private val PUNCTUATION = listOf(',', '.', '!', '?')

    override fun translate(text: String): String = uwuify(text)

    /**
     * Converts normal text into uwu speak
     *
     * @param input The text to convert
     * @return The uwu-ified text
     */
    private fun uwuify(input: String): String {
        var output = input

        output = replaceWords(output)
        output = nyaify(output)
        output = replaceLetters(output)
        output = addStutters(output)
        output = addEmojis(output)

        return output
    }

    /**
     * Replaces specific words with their uwu equivalents
     */
    private fun replaceWords(text: String): String {
        var output = text
        var find = output.findAnyOf(WORDS.keys, ignoreCase = true)

        while (find != null) {
            val word = output.substring(find.first, find.first + find.second.length)
            var replace = WORDS[find.second]!!

            // Preserve capitalization
            if (!word.toCharArray().any { it.isLowerCase() }) {
                // All caps
                replace = replace.uppercase()
            } else if (word[0].isUpperCase()) {
                // First char is uppercase
                replace = replace.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                }
            }

            output = output.replace(word, replace)
            find = output.findAnyOf(WORDS.keys, ignoreCase = true)
        }

        return output
    }

    /**
     * Replaces "na" with "nya" in various capitalizations
     */
    private fun nyaify(text: String): String {
        var output = text
        output = output.replace("na", "nya")
        output = output.replace("Na", "Nya")
        output = output.replace("nA", "nyA")
        output = output.replace("NA", "NYA")
        return output
    }

    /**
     * Replaces 'l' and 'r' with 'w'
     */
    private fun replaceLetters(text: String): String {
        var output = text
        output = output.replace('l', 'w')
        output = output.replace('r', 'w')
        output = output.replace('L', 'W')
        output = output.replace('R', 'W')
        return output
    }

    /**
     * Adds stutters to some words
     */
    private fun addStutters(text: String): String {
        var output = text
        var offset = 0

        for (s in output.split(" ")) {
            if (s.length > 1 && randomWithChance(STUTTER_CHANCE)) {
                output = output.prefixWord(s, "${s[0]}-", offset)
            }
            offset += s.length + 1 // +1 for the space
        }

        return output
    }

    /**
     * Adds emojis to the text
     */
    private fun addEmojis(text: String): String {
        var output = text

        // Add emoji at the end if there's no punctuation
        if (output.isNotEmpty() && !PUNCTUATION.contains(output.last()) && randomWithChance(EMOJI_CHANCE)) {
            output += EMOJIS.random()
        }

        // Add emojis after punctuation
        val array = output.toCharArray()
        for ((eOffset, char) in array.withIndex()) {
            val index = array.indexOf(char)
            if (PUNCTUATION.contains(char) &&
                (index == array.size - 1 || array[index + 1] == ' ') &&
                randomWithChance(EMOJI_CHANCE)
            ) {
                output = output.suffixChar(char, EMOJIS.random(), eOffset)
            }
        }

        return output
    }

    /**
     * Adds a prefix before a word in a string
     */
    private fun String.prefixWord(word: String, prefix: String, startIndex: Int = 0): String {
        val wordIndex = indexOf(word, startIndex)
        if (wordIndex == -1) return this
        return substring(0, wordIndex) + prefix + substring(wordIndex)
    }

    /**
     * Adds a suffix after a character in a string
     */
    private fun String.suffixChar(char: Char, suffix: String, startIndex: Int = 0): String {
        val charIndex = indexOf(char, startIndex)
        if (charIndex == -1) return this
        return substring(0, charIndex + 1) + suffix + substring(charIndex + 1)
    }

    /**
     * Returns true with the given percentage chance
     */
    private fun randomWithChance(chance: Int): Boolean = Random.nextInt(1, 101) <= chance

    override fun toString(): String = "Uwu"

    @Composable
    override fun getColor(): Color = Color(0xffEEC7FF)

    @Composable
    override fun getIcon(): ImageVector = Icons.Default.EmojiEmotions
}
