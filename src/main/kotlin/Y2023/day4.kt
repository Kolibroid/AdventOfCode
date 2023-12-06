package Y2023

import Day

val day4 = Day(2023, 4) {
    val input by fetchInput()


    fun String.toNumbers(): List<Int> = split("\\s+".toRegex())
        .filter(String::isNotEmpty)
        .map(String::toInt)

    infix fun Int.pow(other: Int) = toBigDecimal().pow(other).toInt()

    // Convert a list of games to a list of counts
    fun List<String>.toCount(): List<Int> =
        // Convert to 2 lists of integers
        map {
            val (winning, found) = it.split(": ").last().split("|")
            Pair(winning.toNumbers(), found.toNumbers())
        }
        // Count up the number elements that appear in the winning set
        .map {
            it.second.count { elem ->
                elem in it.first
            }
        }

    // === Part one === //
    puzzle {
        input.lines()
            .filter(String::isNotEmpty)
            .toCount()
            // Sum the scores of these games
            .sumOf {
                if (it == 0) {
                    0
                } else {
                    2 pow (it-1)
                }
            }.toString()
    }

    // === Part two === //
    puzzle {
        val counts = input.lines()
            .filter(String::isNotEmpty)
            .toCount()

        // Calculate the number of each scratchcard that has been won
        val multiples = MutableList(counts.size) { 1 }

        counts.withIndex().forEach {
            for (i in (1..it.value)) {
                multiples[i + it.index] += multiples[it.index]
            }
        }

        // Then sum the total number of cards
        multiples.sum().toString()
    }
}