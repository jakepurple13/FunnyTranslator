package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

data object ShakespeareTranslator : Translator {

    override val lottiePath: String = "fencing.json"

    private val modernToShakespearean = mapOf(
        // Greetings and farewells
        "hello" to "hail",
        "hi" to "good morrow",
        "hey" to "well met",
        "goodbye" to "fare thee well",
        "bye" to "adieu",
        "farewell" to "parting is such sweet sorrow",

        // People and relationships
        "friend" to "gentle companion",
        "friends" to "dear companions",
        "enemy" to "foul villain",
        "enemies" to "knaves",
        "person" to "fellow",
        "people" to "folk",
        "man" to "gentleman",
        "woman" to "fair maiden",
        "boy" to "young squire",
        "girl" to "young maiden",
        "child" to "young one",
        "children" to "little ones",
        "baby" to "babe",
        "family" to "kin",
        "parent" to "sire",
        "father" to "noble father",
        "mother" to "gentle mother",
        "brother" to "good brother",
        "sister" to "fair sister",
        "husband" to "lord husband",
        "wife" to "lady wife",
        "lover" to "paramour",
        "boyfriend" to "suitor",
        "girlfriend" to "lady love",

        // Common responses
        "yes" to "aye",
        "no" to "nay",
        "maybe" to "perchance",
        "perhaps" to "mayhap",
        "sorry" to "I beseech thy forgiveness",
        "thanks" to "I thank thee",
        "thank you" to "I am in thy debt",
        "please" to "prithee",
        "ok" to "very well",
        "okay" to "as you wish",
        "sure" to "indeed",
        "definitely" to "most assuredly",

        // Adjectives
        "cool" to "most wondrous",
        "awesome" to "most excellent",
        "amazing" to "marvelous beyond compare",
        "good" to "most excellent",
        "great" to "most grand",
        "bad" to "most foul",
        "terrible" to "most dreadful",
        "happy" to "full of mirth",
        "sad" to "full of sorrow",
        "angry" to "wrathful",
        "scared" to "affrighted",
        "afraid" to "full of dread",
        "brave" to "valiant",
        "strong" to "mighty",
        "weak" to "feeble",
        "beautiful" to "fair",
        "handsome" to "comely",
        "ugly" to "ill-favored",
        "smart" to "wise",
        "stupid" to "foolish",
        "funny" to "mirthful",
        "serious" to "grave",
        "important" to "of great import",
        "big" to "vast",
        "small" to "diminutive",
        "old" to "ancient",
        "new" to "fresh",
        "young" to "youthful",

        // Verbs
        "love" to "adore",
        "hate" to "detest",
        "like" to "favor",
        "dislike" to "disfavor",
        "want" to "desire",
        "need" to "require",
        "think" to "ponder",
        "believe" to "deem",
        "know" to "wot",
        "understand" to "comprehend",
        "see" to "behold",
        "look" to "gaze upon",
        "watch" to "observe",
        "hear" to "hearken to",
        "listen" to "give ear to",
        "speak" to "utter",
        "say" to "declare",
        "tell" to "recount",
        "ask" to "inquire",
        "answer" to "reply",
        "eat" to "feast upon",
        "drink" to "quaff",
        "sleep" to "slumber",
        "wake" to "arise",
        "talk" to "speak",
        "walk" to "promenade",
        "run" to "make haste",
        "fight" to "duel",
        "kill" to "slay",
        "die" to "perish",
        "live" to "dwell",
        "work" to "labor",
        "play" to "frolic",
        "laugh" to "make merry",
        "cry" to "weep",
        "smile" to "beam",
        "frown" to "scowl",
        "give" to "bestow",
        "take" to "seize",
        "find" to "discover",
        "lose" to "misplace",
        "begin" to "commence",
        "end" to "conclude",
        "help" to "aid",
        "hurt" to "harm",
        "wait" to "tarry",
        "go" to "proceed",
        "come" to "approach",
        "leave" to "depart",
        "stay" to "remain",
        "return" to "come back",

        // Nouns
        "money" to "gold",
        "house" to "dwelling",
        "home" to "abode",
        "room" to "chamber",
        "bed" to "resting place",
        "table" to "board",
        "chair" to "seat",
        "door" to "portal",
        "window" to "casement",
        "car" to "carriage",
        "vehicle" to "conveyance",
        "phone" to "speaking device",
        "computer" to "thinking machine",
        "internet" to "vast web of knowledge",
        "email" to "electronic missive",
        "text" to "written word",
        "message" to "proclamation",
        "photo" to "likeness",
        "picture" to "portrait",
        "music" to "melody",
        "song" to "ballad",
        "book" to "tome",
        "story" to "tale",
        "food" to "victuals",
        "drink" to "libation",
        "water" to "aqua",
        "wine" to "nectar",
        "beer" to "ale",
        "clothes" to "garments",
        "hat" to "cap",
        "shoes" to "footwear",
        "time" to "hour",
        "day" to "morrow",
        "night" to "eve",
        "morning" to "dawn",
        "evening" to "dusk",
        "week" to "sennight",
        "month" to "moon",
        "year" to "twelvemonth",
        "place" to "locale",
        "city" to "metropolis",
        "town" to "hamlet",
        "country" to "realm",
        "world" to "globe",
        "weather" to "clime",
        "rain" to "precipitation",
        "snow" to "winter's coat",
        "wind" to "zephyr",
        "sun" to "sol",
        "moon" to "luna",
        "star" to "celestial body",
        "sky" to "heavens",
        "earth" to "soil",
        "sea" to "ocean",
        "river" to "stream",
        "mountain" to "peak",
        "forest" to "wood",
        "tree" to "timber",
        "flower" to "bloom",
        "animal" to "beast",
        "bird" to "fowl",
        "fish" to "aquatic creature",
        "dog" to "hound",
        "cat" to "feline",
        "horse" to "steed"
    )

    // Shakespearean sentence starters
    private val sentenceStarters = listOf(
        "Hark! ",
        "Alas, ",
        "Forsooth, ",
        "Verily, ",
        "Lo! ",
        "Prithee, ",
        "Pray, ",
        "By my troth, ",
        "In faith, ",
        "Alack! ",
        "Marry, ",
        "Zounds! ",
        "Fie! ",
        "Odds bodkins! ",
        "Gadzooks! ",
        "Hear ye! ",
        "Harken, ",
        "Good my lord, ",
        "Fair maiden, ",
        "Gentle sir, ",
        "Noble friend, ",
        "What ho! "
    )

    // Shakespearean sentence enders
    private val sentenceEnders = mapOf(
        "." to listOf(
            ", forsooth.",
            ", indeed.",
            ", verily.",
            ", in truth.",
            ", 'tis true.",
            ", mark my words.",
            ", by my faith.",
            ", I say.",
            ", good sir.",
            ", good madam.",
            ", I warrant thee.",
            ", I assure thee.",
            ", without doubt.",
            ", beyond question.",
            ", as I live and breathe."
        ),
        "!" to listOf(
            ", by my troth!",
            ", I swear!",
            ", upon my honor!",
            ", odds blood!",
            ", zounds!",
            ", what say you!",
            ", mark me!",
            ", hear me now!",
            ", 'sblood!",
            ", marry!",
            ", alack!",
            ", fie!",
            ", egad!",
            ", gadzooks!",
            ", God's wounds!"
        ),
        "?" to listOf(
            ", pray tell?",
            ", dost thou not agree?",
            ", wouldst thou not?",
            ", I beseech thee?",
            ", perchance?",
            ", good sir?",
            ", fair maiden?",
            ", knowest thou?",
            ", dost thou comprehend?",
            ", art thou certain?",
            ", what say you?",
            ", is't not so?",
            ", think'st thou not?",
            ", canst thou believe?",
            ", how now?"
        )
    )

    // Verb conjugation patterns
    private val verbConjugations = mapOf(
        "\\b(I|we|they) am\\b".toRegex() to "\\1 be",
        "\\b(I|we|they) are\\b".toRegex() to "\\1 be",
        "\\b(I|we|they) was\\b".toRegex() to "\\1 wast",
        "\\b(I|we|they) were\\b".toRegex() to "\\1 wert",
        "\\b(I|we|they) have\\b".toRegex() to "\\1 hath",
        "\\b(I|we|they) has\\b".toRegex() to "\\1 hath",
        "\\b(I|we|they) do\\b".toRegex() to "\\1 doth",
        "\\b(I|we|they) does\\b".toRegex() to "\\1 doth",
        "\\byou are\\b".toRegex(RegexOption.IGNORE_CASE) to "thou art",
        "\\byou were\\b".toRegex(RegexOption.IGNORE_CASE) to "thou wert",
        "\\byou have\\b".toRegex(RegexOption.IGNORE_CASE) to "thou hast",
        "\\byou do\\b".toRegex(RegexOption.IGNORE_CASE) to "thou dost",
        "\\byou would\\b".toRegex(RegexOption.IGNORE_CASE) to "thou wouldst",
        "\\byou could\\b".toRegex(RegexOption.IGNORE_CASE) to "thou couldst",
        "\\byou should\\b".toRegex(RegexOption.IGNORE_CASE) to "thou shouldst",
        "\\byou will\\b".toRegex(RegexOption.IGNORE_CASE) to "thou wilt",
        "\\byou shall\\b".toRegex(RegexOption.IGNORE_CASE) to "thou shalt",
        "\\byou can\\b".toRegex(RegexOption.IGNORE_CASE) to "thou canst",
        "\\byou may\\b".toRegex(RegexOption.IGNORE_CASE) to "thou mayst",
        "\\byou must\\b".toRegex(RegexOption.IGNORE_CASE) to "thou must",
        "\\bhe is\\b".toRegex(RegexOption.IGNORE_CASE) to "he be",
        "\\bshe is\\b".toRegex(RegexOption.IGNORE_CASE) to "she be",
        "\\bit is\\b".toRegex(RegexOption.IGNORE_CASE) to "it be",
        "\\bthey are\\b".toRegex(RegexOption.IGNORE_CASE) to "they be",
        "\\bwe are\\b".toRegex(RegexOption.IGNORE_CASE) to "we be",
        "\\bis\\b".toRegex() to "be",
        "\\bare\\b".toRegex() to "be",
        "\\bwas\\b".toRegex() to "wast",
        "\\bwere\\b".toRegex() to "wert"
    )

    // Pronoun replacements
    private val pronounReplacements = mapOf(
        "\\byour\\b".toRegex(RegexOption.IGNORE_CASE) to "thy",
        "\\byours\\b".toRegex(RegexOption.IGNORE_CASE) to "thine",
        "\\byou\\b".toRegex(RegexOption.IGNORE_CASE) to "thou",
        "\\byourself\\b".toRegex(RegexOption.IGNORE_CASE) to "thyself",
        "\\bmy\\b".toRegex(RegexOption.IGNORE_CASE) to "mine",
        "\\bhis\\b".toRegex(RegexOption.IGNORE_CASE) to "his"
    )

    override fun translate(text: String): String {
        if (text.isBlank()) return text

        // Split into sentences
        val sentences = text.split(Regex("[.!?]")).filter { it.isNotBlank() }
        if (sentences.isEmpty()) return shakespearify(text)

        // Process each sentence
        return sentences.joinToString(" ") { sentence ->
            shakespearify(sentence)
        }
    }

    private fun shakespearify(sentence: String): String {
        var result = sentence.trim()
        // Apply verb conjugations
        verbConjugations.forEach { (pattern, replacement) ->
            result = result.replace(pattern, replacement)
        }

        // Apply pronoun replacements
        pronounReplacements.forEach { (pattern, replacement) ->
            result = result.replace(pattern, replacement)
        }

        // Replace words with Shakespearean equivalents
        modernToShakespearean.forEach { (modern, shakespearean) ->
            result = result.replace("\\b$modern\\b".toRegex(RegexOption.IGNORE_CASE), shakespearean)
        }

        // Add sentence starter (30% chance)
        if (Random.nextInt(100) < 30 && result.length > 5) {
            result = sentenceStarters.random() + result.lowercase()
        }

        // Add Shakespearean ending based on punctuation
        runCatching {
            result = when {
                result.endsWith(".") -> {
                    result.dropLast(1) + sentenceEnders["."]!!.random()
                }

                result.endsWith("!") -> {
                    result.dropLast(1) + sentenceEnders["!"]!!.random()
                }

                result.endsWith("?") -> {
                    result.dropLast(1) + sentenceEnders["?"]!!.random()
                }

                else -> {
                    // If no punctuation, add a random ending
                    val randomEnding = when (Random.nextInt(3)) {
                        0 -> sentenceEnders["."]!!.random()
                        1 -> sentenceEnders["!"]!!.random()
                        else -> sentenceEnders["?"]!!.random()
                    }
                    "$result$randomEnding"
                }
            }
        }

        return result
    }

    override fun toString(): String = "Shakespeare"

    @Composable
    override fun getColor(): Color {
        return Color(0xff023094)
    }

    @Composable
    override fun getIcon(): ImageVector {
        return Icons.Default.HistoryEdu
    }
}
