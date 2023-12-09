package Y2023

import Day


class HandComparator(val cardOrder: String, val cardRank: (String) -> Int): Comparator<String> {
    override fun compare(o1: String?, o2: String?): Int {
        val type1 = cardRank(o1!!)
        val type2 = cardRank(o2!!)

        if (type1 > type2) {
            return 1
        }
        if (type1 < type2) {
            return -1
        }

        // Otherwise check each card
        for ((c1, c2) in o1.zip(o2)) {
            val cs1 = cardOrder.indexOf(c1)
            val cs2 = cardOrder.indexOf(c2)

            if (cs1 > cs2) {
                return 1
            }
            if (cs1 < cs2) {
                return -1
            }
        }

        // Otherwise equal
        return 0
    }

}

val day7 = Day(2023, 7) {
    val input by fetchInput()


    val handTypes = mapOf(
        listOf(5) to 7,             // Five of a kind
        listOf(1, 4) to 6,          // Four of a kind
        listOf(2, 3) to 5,          // Full house
        listOf(1, 1, 3) to 4,       // Three of a kind
        listOf(1, 2, 2) to 3,       // Two pair
        listOf(1, 1, 1, 2) to 2,    // One pair
        listOf(1, 1, 1, 1, 1) to 1  // High card
    )

    fun typeScore(hand: String): Int {
        val occurrences: MutableMap<Char, Int> = hand.fold(mutableMapOf()) { acc, c ->
            acc.merge(c, 1) { a, b -> a + b }
            acc
        }

        val counts = occurrences.values.sorted()

        return handTypes[counts]!!
    }


    // === Puzzle one === //
    val handComparator = HandComparator("23456789TJQKA", ::typeScore)

    puzzle {
        input.lines()
            .filter(String::isNotEmpty)
            .map {
                val (a, b) = it.split(" ")
                Pair(a, b)
            }.sortedWith { o1, o2 ->
                handComparator.compare(o1.first, o2.first)
            }
            .map {
                it.second.toInt()
            }.withIndex()
            .sumOf {
                it.value * (it.index + 1)
            }.toString()
    }


    // Not the most elegant way to do this, but it works well enough
    fun jokerTypeScore(hand: String): Int {
        // I think that all jokers should always pretend to be the same type to maximize the value
        return "23456789TQKA".maxOf {
            typeScore(hand.replace("J", it.toString()))
        }
    }

    // === Puzzle two === //
    val jokerHandComparator = HandComparator("J23456789TQKA", ::jokerTypeScore)

    puzzle {
        input.lines()
            .filter(String::isNotEmpty)
            .map {
                val (a, b) = it.split(" ")
                Pair(a, b)
            }.sortedWith { o1, o2 ->
                jokerHandComparator.compare(o1.first, o2.first)
            }
            .map {
                it.second.toInt()
            }.withIndex()
            .sumOf {
                it.value * (it.index + 1)
            }.toString()
    }
}