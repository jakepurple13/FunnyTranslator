package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data object ShakespeareTranslator : Translator {

    private val modernToShakespearean = mapOf(
        "hello" to "hail",
        "hi" to "good morrow",
        "goodbye" to "fare thee well",
        "friend" to "gentle companion",
        "yes" to "aye",
        "no" to "nay",
        "sorry" to "I beseech thy forgiveness",
        "thanks" to "I thank thee",
        "thank you" to "I am in thy debt",
        "cool" to "most wondrous",
        "good" to "most excellent",
        "bad" to "most foul",
        "happy" to "full of mirth",
        "sad" to "full of sorrow",
        "angry" to "wrathful",
        "love" to "adore",
        "hate" to "detest",
        "think" to "ponder",
        "see" to "behold",
        "look" to "gaze upon",
        "eat" to "feast upon",
        "drink" to "quaff",
        "sleep" to "slumber",
        "talk" to "speak",
        "walk" to "promenade",
        "run" to "make haste",
        "fight" to "duel",
        "kill" to "slay",
        "die" to "perish",
        "money" to "gold",
        "house" to "dwelling",
        "car" to "carriage",
        "phone" to "speaking device",
        "computer" to "thinking machine",
        "internet" to "vast web of knowledge",
        "email" to "electronic missive",
        "text" to "written word",
        "message" to "proclamation",
        "photo" to "likeness",
        "picture" to "portrait",
        "music" to "melody",
        "song" to "ballad"
    )

    override fun translate(text: String): String {
        var result = text

        // Replace words with Shakespearean equivalents
        modernToShakespearean.forEach { (modern, shakespearean) ->
            result = result.replace("\\b$modern\\b".toRegex(RegexOption.IGNORE_CASE), shakespearean)
        }

        // Add some Shakespearean flair
        result = when {
            result.endsWith(".") -> result.dropLast(1) + ", forsooth."
            result.endsWith("!") -> result.dropLast(1) + ", by my troth!"
            result.endsWith("?") -> result.dropLast(1) + ", pray tell?"
            else -> "$result, verily."
        }

        // Add "thou" and "thy" occasionally
        if (result.length > 10 && !result.contains("thou") && !result.contains("thy")) {
            result = "Thou " + result.replaceFirst("you", "").trim()
        }

        return result
    }

    override fun toString(): String = "Shakespeare Translator"

    @Composable
    override fun getColor(): Color {
        return Color(0xff023094)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.HistoryEdu
    }
}
