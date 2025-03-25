package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

data object Pirate : Translator {
    private val englishToPirate = mapOf(
        // Basic replacements
        "hello" to "ahoy",
        "hi" to "ahoy",
        "hey" to "ahoy",
        "my" to "me",
        "mine" to "me",
        "name" to "name be",
        "is" to "be",
        "am" to "be",
        "are" to "be",
        "was" to "be",
        "were" to "be",
        "friend" to "matey",
        "friends" to "mateys",
        "buddy" to "matey",
        "pal" to "matey",
        "yes" to "aye",
        "yeah" to "aye",
        "yep" to "aye",
        "no" to "nay",
        "nope" to "nay",
        "treasure" to "booty",
        "money" to "gold",
        "gold" to "doubloons",
        "drink" to "grog",
        "drinks" to "grog",
        "beer" to "grog",
        "food" to "grub",
        "eat" to "feast",
        "eating" to "feastin'",
        "man" to "landlubber",
        "woman" to "wench",
        "girl" to "lass",
        "boy" to "lad",
        "child" to "young swab",
        "children" to "little swabs",
        "ocean" to "the high seas",
        "sea" to "the briny deep",
        "water" to "the blue",
        "ship" to "vessel",
        "boat" to "dinghy",
        "captain" to "cap'n",
        "boss" to "cap'n",
        "leader" to "cap'n",
        "look" to "spy",
        "looking" to "spyin'",
        "see" to "spy",
        "seeing" to "spyin'",
        "steal" to "plunder",
        "stealing" to "plunderin'",
        "stole" to "plundered",
        "rob" to "pillage",
        "robbing" to "pillagin'",
        "robbed" to "pillaged",
        "fight" to "battle",
        "fighting" to "battlin'",
        "fought" to "battled",
        "kill" to "send to Davy Jones' locker",
        "killing" to "sendin' to Davy Jones' locker",
        "killed" to "sent to Davy Jones' locker",
        "die" to "walk the plank",
        "dying" to "walkin' the plank",
        "died" to "walked the plank",
        "dead" to "with Davy Jones",
        "death" to "Davy Jones' locker",
        "goodbye" to "farewell",
        "bye" to "farewell",
        "later" to "until next tide",
        "home" to "port",
        "house" to "shanty",
        "building" to "establishment",
        "bar" to "tavern",
        "pub" to "tavern",
        "restaurant" to "galley",
        "bathroom" to "head",
        "toilet" to "head",
        "restroom" to "head",
        "good" to "fine",
        "great" to "mighty fine",
        "excellent" to "grand",
        "bad" to "scurvy",
        "terrible" to "cursed",
        "awful" to "wretched",
        "happy" to "jolly",
        "sad" to "glum",
        "angry" to "mad as a hornets' nest",
        "scared" to "lily-livered",
        "afraid" to "yellow-bellied",
        "brave" to "hearty",
        "strong" to "mighty",
        "weak" to "feeble",
        "smart" to "clever",
        "stupid" to "addled",
        "dumb" to "daft",
        "crazy" to "mad",
        "insane" to "touched in the head",
        "sick" to "ill with the fever",
        "ill" to "under the weather",
        "tired" to "beat",
        "exhausted" to "spent",
        "drunk" to "three sheets to the wind",
        "intoxicated" to "three sheets to the wind",
        "sober" to "dry as a bone",
        "rich" to "loaded with doubloons",
        "poor" to "without a coin to yer name",
        "broke" to "without a coin to yer name",
        "thief" to "scallywag",
        "thieve" to "scallywag",
        "criminal" to "scoundrel",
        "police" to "navy dogs",
        "cop" to "navy dog",
        "officer" to "navy dog",
        "jail" to "brig",
        "prison" to "brig",
        "weapon" to "arms",
        "gun" to "flintlock",
        "pistol" to "flintlock",
        "rifle" to "musket",
        "sword" to "cutlass",
        "knife" to "dagger",
        "dagger" to "dirk",
        "the" to "th'",
        "you" to "ye",
        "your" to "yer",
        "yours" to "yers",
        "myself" to "meself",
        "himself" to "hisself",
        "herself" to "herself",
        "themselves" to "themselves",
        "ourselves" to "ourselves",
        "yourself" to "yerself",
        "yourselves" to "yerselves",
        "I" to "I",
        "me" to "me",
        "him" to "him",
        "her" to "her",
        "them" to "them",
        "us" to "us",
        "we" to "we",
        "he" to "he",
        "she" to "she",
        "they" to "they",
        "it" to "it",
        "its" to "its",
        "this" to "this",
        "that" to "that",
        "these" to "these",
        "those" to "those",
        "here" to "here",
        "there" to "there",
        "where" to "where",
        "when" to "when",
        "why" to "why",
        "how" to "how",
        "what" to "what",
        "who" to "who",
        "which" to "which",
        "whose" to "whose",
        "whom" to "whom"
    )

    private val pirateInterjections = listOf(
        "Arr!",
        "Yarr!",
        "Avast!",
        "Yo-ho-ho!",
        "Shiver me timbers!",
        "Blimey!",
        "By Blackbeard's beard!",
        "Ahoy there!",
        "Aye aye!",
        "Heave ho!"
    )

    private val piratePhraseStarters = listOf(
        "Arr, ",
        "Yarr, ",
        "Avast ye, ",
        "Ahoy matey, ",
        "Yo-ho-ho, ",
        "Shiver me timbers, ",
        "Blimey, ",
        "By the powers, ",
        "Sail ho, ",
        "Heave to, "
    )

    private val piratePhraseEnders = listOf(
        ", arr!",
        ", yarr!",
        ", ye scurvy dog!",
        ", savvy?",
        ", ye landlubber!",
        ", or walk the plank!",
        ", by Davy Jones' locker!",
        ", and a bottle o' rum!",
        ", me hearty!",
        ", ye scallywag!"
    )

    override fun translate(text: String): String {
        if (text.isBlank()) return text

        // Split into sentences
        val sentences = text.split(Regex("[.!?]")).filter { it.isNotBlank() }
        if (sentences.isEmpty()) return translateToPirate(text)

        // Process each sentence
        return sentences.joinToString(" ") { sentence ->
            translateToPirate(sentence)
        }
    }

    private fun translateToPirate(sentence: String): String {
        var result = sentence.trim()

        // Add pirate phrase starter (30% chance)
        if (Random.nextInt(100) < 30) {
            result = piratePhraseStarters.random() + result.lowercase()
        }

        // Replace words while preserving case
        val words = result.split(Regex("\\s+"))
        val translatedWords = words.map { word ->
            // Extract any punctuation at the beginning or end of the word
            val prefix = word.takeWhile { !it.isLetterOrDigit() }
            val suffix = word.takeLastWhile { !it.isLetterOrDigit() }
            val cleanWord = word.drop(prefix.length).dropLast(suffix.length)

            if (cleanWord.isEmpty()) return@map word

            // Check for word in dictionary with case variations
            val replacement = when {
                englishToPirate.containsKey(cleanWord) -> englishToPirate[cleanWord]
                englishToPirate.containsKey(cleanWord.lowercase()) -> {
                    val rep = englishToPirate[cleanWord.lowercase()]
                    // Preserve capitalization
                    if (cleanWord[0].isUpperCase() && rep != null && rep.isNotEmpty()) {
                        rep[0].uppercase() + rep.drop(1)
                    } else {
                        rep
                    }
                }

                else -> cleanWord
            }

            // Reassemble word with punctuation
            prefix + (replacement ?: cleanWord) + suffix
        }

        result = translatedWords.joinToString(" ")

        // Add random pirate interjection (10% chance)
        if (Random.nextInt(100) < 10) {
            result = pirateInterjections.random() + " " + result
        }

        // Add pirate phrase ender (40% chance)
        if (Random.nextInt(100) < 40 && !result.endsWith("!") && !result.endsWith("?")) {
            result += piratePhraseEnders.random()
        }

        return result
    }

    override fun toString(): String = "Pirate Translator"

    @Composable
    override fun getColor(): Color {
        return Color(0xffFF0000)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.Sailing
    }
}
