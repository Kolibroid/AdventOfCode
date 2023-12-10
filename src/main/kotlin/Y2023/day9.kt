package Y2023

import Day

val day9 = Day(2023, 9) {
    val input by fetchInput()

    // This whole day was surprisingly easy...?

    fun predictSequence(values: List<Long>): Long {
        if (values.all { it == 0L }) {
            return 0
        }

        val differences = values.windowed(2)
            .map { it[1]-it[0] }

        return values.last() + predictSequence(differences)
    }

    // === Part one === //
    puzzle {
        input.lines()
            .filter(String::isNotEmpty)
            .map {
                it.split(" ")
                    .filter(String::isNotEmpty)
                    .map(String::toLong)
            }.sumOf(::predictSequence)
            .toString()
    }

    // === Part two === //
    puzzle {
        input.lines()
            .filter(String::isNotEmpty)
            .map {
                it.split(" ")
                    .filter(String::isNotEmpty)
                    .map(String::toLong)
                    .reversed()  // Just reverse the order of the values to extrapolate backwards
            }.sumOf(::predictSequence)
            .toString()
    }
}