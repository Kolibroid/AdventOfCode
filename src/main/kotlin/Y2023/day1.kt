package Y2023

import Day


val day1 = Day(2023, 1) {
    val input by fetchInput()

    // Create mapping of digit names to integer counterparts
    val digitMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "0" to 0,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

    // Scan through a string until one of the keys is found, and return the corresponding value
    fun<T> String.scanFirst(dict: Map<String, T>, reverse: Boolean = false): T? {
        val indices = if (reverse) {(length downTo 0)} else {(0..length)}

        for (i in indices) {
            for ((key, value) in dict.entries) {
                if (startsWith(key, startIndex=i)) {
                    return value
                }
            }
        }

        return null
    }


    // === First part === //
    puzzle {
        input.split('\n')
            .filter(kotlin.String::isNotEmpty)
            .sumOf {
                10 * it.first(kotlin.Char::isDigit).digitToInt() + it.last(kotlin.Char::isDigit).digitToInt()
            }.toString()
    }


    // === Second part === //
    puzzle {
        input.split('\n')
            .filter(kotlin.String::isNotEmpty)
            .sumOf {
                10 * it.scanFirst(digitMap)!! + it.scanFirst(digitMap, true)!!
            }.toString()
    }
}
