package com.gabb.funnytranslator.translators

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data object ZombieTranslator : Translator {

    private val wordReplacements = mapOf(
        "hello" to "graaah",
        "hi" to "urrrgh",
        "hey" to "braaains",
        "goodbye" to "groooan",
        "bye" to "urrrgh",
        "yes" to "grrrr",
        "no" to "nrrrgh",
        "food" to "braaains",
        "eat" to "devouuur",
        "hungry" to "need braaains",
        "friend" to "fresh meaaat",
        "person" to "meaaat",
        "people" to "meaaat",
        "human" to "meaaat",
        "humans" to "meaaat",
        "man" to "meaaat",
        "woman" to "meaaat",
        "child" to "small meaaat",
        "children" to "small meaaat",
        "run" to "shamble",
        "walk" to "shamble",
        "go" to "shamble",
        "fast" to "slow",
        "quickly" to "slowly",
        "speed" to "shamble",
        "talk" to "grooooan",
        "speak" to "grooooan",
        "say" to "grooooan",
        "said" to "groooaned",
        "think" to "hunger",
        "thinking" to "hungering",
        "thought" to "hungered",
        "smart" to "mindless",
        "intelligent" to "mindless",
        "brain" to "braaain",
        "brains" to "braaains",
        "dead" to "undead",
        "alive" to "undead",
        "living" to "soon-to-be-undead",
        "life" to "undeath"
    )

    private val zombieGroans = listOf(
        "Urrrgh...",
        "Graaah...",
        "Braaains...",
        "Nnnngh...",
        "Hrrrngh...",
        "Mrrrrh..."
    )

    private val sentenceStarters = listOf(
        "Urrrgh... ",
        "Braaains... ",
        "Graaah... ",
        "Hungry... ",
        "Meaaat... ",
        "Must... eat... "
    )

    private val sentenceEnders = listOf(
        "... braaains.",
        "... urrrgh.",
        "... hungry.",
        "... meaaat.",
        "... graaah.",
        "... need... braaains."
    )

    private val vowels = setOf('a', 'e', 'i', 'o', 'u')

    // Extension function for random chance
    private inline fun Int.percentChance(block: () -> Unit) {
        if (Random.nextInt(100) < this) block()
    }

    override fun translate(text: String): String = when {
        text.isBlank() -> text
        else -> text
            .split(Regex("[.!?]"))
            .filter { it.isNotBlank() }
            .ifEmpty { listOf(text) }
            .joinToString(" ") { zombify(it) }
    }

    private fun zombify(sentence: String): String {
        // Start with trimmed sentence and apply transformations
        return sentence.trim().let { trimmed ->
            // Apply sentence starter (40% chance)
            var result = trimmed
            40.percentChance {
                result = sentenceStarters.random() + trimmed.lowercase()
            }

            // Replace words
            result = wordReplacements.entries.fold(result) { acc, (original, replacement) ->
                acc.replace(Regex("\\b$original\\b", RegexOption.IGNORE_CASE), replacement)
            }

            // Elongate vowels (60% chance)
            60.percentChance {
                result = elongateVowels(result)
            }

            // Insert random zombie groan (30% chance)
            30.percentChance {
                val words = result.split(" ").toMutableList()
                words.add(Random.nextInt(words.size), zombieGroans.random())
                result = words.joinToString(" ")
            }

            // Add zombie sentence ender (50% chance)
            if (!result.endsWith('.') && !result.endsWith('!') && !result.endsWith('?')) {
                50.percentChance {
                    result += sentenceEnders.random()
                }
            }

            result
        }
    }

    private fun elongateVowels(text: String): String = buildString {
        text.forEach { char ->
            append(char)

            // If it's a vowel, repeat it 1-3 times (with 40% chance)
            if (char.lowercaseChar() in vowels) {
                40.percentChance {
                    repeat(Random.nextInt(1, 4)) {
                        append(char)
                    }
                }
            }
        }
    }

    override fun toString(): String = "Zombie Translator"

    @Composable
    override fun getColor(): Color = Color(0xff4CAF50) // Zombie green
}
