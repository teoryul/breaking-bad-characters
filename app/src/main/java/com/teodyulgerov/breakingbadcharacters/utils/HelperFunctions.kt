package com.teodyulgerov.breakingbadcharacters.utils

/**
 * Converts a String list to a bulleted String to be displayed in a TextView.
 */
fun convertStringListToBulletedString(list: List<String>): String {
    var sentence = "";
    list.mapIndexed { index, string ->
        sentence += "\u25CF $string" + if (index < list.size - 1) "\n" else ""
    }

    return sentence
}