package com.gabb.funnytranslator.translators

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.random.Random

data object HackerTranslator : Translator {

    override val lottiePath: String = "folder.json"

    private val leetSpeakMap = mapOf(
        'a' to listOf("4", "@", "/-\\"),
        'b' to listOf("8", "|3", "13"),
        'c' to listOf("(", "{", "["),
        'd' to listOf("|)", "|}"),
        'e' to listOf("3", "€"),
        'f' to listOf("ph", "|="),
        'g' to listOf("6", "9", "&"),
        'h' to listOf("#", "|-|"),
        'i' to listOf("1", "!", "|"),
        'j' to listOf("_|", "¿"),
        'k' to listOf("|<", "|{"),
        'l' to listOf("1", "|_", "|"),
        'm' to listOf("|v|", "/\\/\\"),
        'n' to listOf("|\\|", "/\\/"),
        'o' to listOf("0", "()", "[]"),
        'p' to listOf("|°", "|>"),
        'q' to listOf("(,)", "9"),
        'r' to listOf("|2", "12", "®"),
        's' to listOf("5", "$", "§"),
        't' to listOf("7", "+", "†"),
        'u' to listOf("|_|", "µ"),
        'v' to listOf("\\/", "\\|"),
        'w' to listOf("\\/\\/", "vv"),
        'x' to listOf("><", ")("),
        'y' to listOf("¥", "\\|/"),
        'z' to listOf("2", "7_")
    )

    private val techJargon = listOf(
        "sudo", "chmod", "hack", "exploit", "firewall", "backdoor", "rootkit",
        "malware", "encryption", "decryption", "algorithm", "buffer", "overflow",
        "injection", "payload", "shell", "terminal", "kernel", "daemon", "ping",
        "traceroute", "ssh", "ftp", "http", "https", "tcp/ip", "ddos", "botnet",
        "zero-day", "vulnerability", "patch", "exploit", "breach", "authentication"
    )

    private val hackerPhrases = listOf(
        "I'm in the mainframe",
        "Bypassing security protocols",
        "Cracking the encryption",
        "Executing the exploit",
        "Deploying the payload",
        "Accessing root privileges",
        "Initiating the backdoor",
        "Compiling the code",
        "Injecting the script",
        "Brute forcing the password"
    )

    private val sentenceStarters = listOf(
        "$ ",
        "> ",
        "# ",
        "root@system:~# ",
        "hacker@terminal:~$ ",
        "C:\\> ",
        "admin@server:~# "
    )

    private val sentenceEnders = listOf(
        " <EOF>",
        " [DONE]",
        " [SUCCESS]",
        " [HACKED]",
        " [SYSTEM COMPROMISED]",
        " [ACCESS GRANTED]",
        " [FIREWALL BYPASSED]",
        " [SECURITY BREACH]"
    )

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
            .joinToString(" ") { hackerize(it) }
    }

    private fun hackerize(sentence: String): String {
        // Start with trimmed sentence and apply transformations
        return sentence.trim().let { trimmed ->
            // Apply sentence starter (60% chance)
            var result = trimmed
            60.percentChance {
                result = sentenceStarters.random() + trimmed
            }

            // Convert to leetspeak (70% chance)
            70.percentChance {
                result = toLeetSpeak(result)
            }

            // Insert random tech jargon (40% chance)
            40.percentChance {
                val words = result.split(" ").toMutableList()
                val position = Random.nextInt(words.size)
                words.add(position, techJargon.random())
                result = words.joinToString(" ")
            }

            // Replace entire sentence with hacker phrase (10% chance)
            10.percentChance {
                result = hackerPhrases.random()
            }

            // Add hacker sentence ender (30% chance)
            if (!result.endsWith('.') && !result.endsWith('!') && !result.endsWith('?')) {
                30.percentChance {
                    result += sentenceEnders.random()
                }
            }

            result
        }
    }

    private fun toLeetSpeak(text: String): String = buildString {
        text.forEach { char ->
            if (char.isLetter() && Random.nextInt(100) < 70) {
                val options = leetSpeakMap[char.lowercaseChar()]
                if (options != null) {
                    append(options.random())
                } else {
                    append(char)
                }
            } else {
                append(char)
            }
        }
    }

    override fun toString(): String = "Hacker"

    @Composable
    override fun getColor(): Color = Color(0xFF00FF00) // Matrix green

    @Composable
    override fun getIcon(): ImageVector = Icons.Default.Code
}
