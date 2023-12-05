package Y2023

import Day


val day2 = Day(2023, 2) {
    val input by fetchInput()


    // Convert a line to a map of colour: max count
    fun maximumCounts(game: String): Map<String, Int> {
        val (_, pulls) = game.split(": ")

        val colourMax = mutableMapOf(
            "red" to 0,
            "green" to 0,
            "blue" to 0,
        )

        pulls.split("[,;] ".toRegex()).forEach {
            val (num, col) = it.split(" ")
            colourMax[col] = maxOf(num.toInt(), colourMax[col]!!)
        }

        return colourMax
    }


    // === Part 1 === //
    puzzle {
        input.lines()
            .filter(String::isNotEmpty)
            // Convert each line to a map of colour: max count
            .map(::maximumCounts)
            .withIndex()
            .filter {
                it.value["red"]!! <= 12
                && it.value["green"]!! <= 13
                && it.value["blue"]!! <= 14
            }
            .sumOf {
                    it.index+1
            }.toString()
    }

    // === Part 2 === //
    puzzle {
        input.lines()
            .filter(String::isNotEmpty)
            .map(::maximumCounts)
            .sumOf {
                it.values.fold(1, Int::times)
            }.toString()
    }
}