package com.gabb.funnytranslator.translators

data object MorseCode: Translator {
    override fun translate(text: String): String {
        return encode(text)
    }

    fun encode(message: String): String {
        var callDashDotLetterWarning = false
        var newMessage = ""
        /* lookahead Regex, allows to keep the delimiter */
        val delimiter = Regex("(?<=\\s)|(?=\\s)")

        val normalizedMessage: String = normalize(message)

        val words = normalizedMessage.split(delimiter)
        for (word in words) {
            for (letter in word) {
                if (letter == '-' || letter == '.') {
                    callDashDotLetterWarning = true
                }

                newMessage += replaceWithMorseLetter(letter.lowercaseChar().toString())
            }
        }

        if (callDashDotLetterWarning) {
            println("WARNING: The message you entered contained dots (.) or dashes (-), these have been highlighted with < >")
        }

        return newMessage.trim()
    }

    /**
     * @param letter Natural letter
     * @return Equivalent [morse code][Alphabet.morseAlphabet] letter.
     */
    private fun replaceWithMorseLetter(letter: String): String {
        /* So there is no confusion with the characters of the morse code */
        if (letter == "-" || letter == ".") return "<$letter> "

        if (!naturalAlphabet.contains(letter)) return "$letter "

        return morseAlphabet[naturalAlphabet.indexOf(letter)] + " "
    }

    val naturalAlphabet = arrayOf(
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
        "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
        "u", "v", "w", "x", "y", "z",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " "
    )
    val morseAlphabet = arrayOf(
        ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
        "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
        "..-", "...-", ".--", "-..-", "-.--", "--..",
        ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----", "/"
    )

    /**
     * @param letter Letter to be searched.
     * @return If any alphabet contains the letter.
     */
    fun isUnknownLetter(letter: String): Boolean {
        return !(naturalAlphabet.contains(letter) || morseAlphabet.contains(letter))
    }

    /**
     * @param message Input string.
     * @return Message without multiple spaces, accentuated or diacritics.
     */
    fun normalize(message: String): String {
        /* remove multiple spaces */
        val newMessage: String = message.replace("\\s+".toRegex(), " ")
        /* remove accentuated and diacritics */
        return newMessage.replace("\\p{Mn}+".toRegex(), "")
    }

    override fun toString(): String = "Morse Code Translator"
}