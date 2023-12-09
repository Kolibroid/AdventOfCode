package Y2023

import Day
import kotlin.math.max
import kotlin.math.min


class Range(val dest: Long, val source: Long, val length: Long) {
    fun maps(x: Long) = source <= x && x < source+length

    fun map(x: Long): Long {
        if (!maps(x)) {
            throw IllegalArgumentException("Not in range")
        }

        return x-source + dest
    }
}

class Mapping(val entries: List<Range>) {
    companion object {
        fun fromString(entries: String) = Mapping(
            entries.lines()
                .filter(String::isNotEmpty)
                .map {
                    val (dest, src, len) = it.split(" ")
                    Range(dest.toLong(), src.toLong(), len.toLong())
                }
        )
    }

    val pseudoEntries by lazy {
        val pe: MutableList<Range> = mutableListOf()

        // Add pseudo-entries to account for unmapped areas
        var lastEnd = 0L
        for (entry in entries.sortedBy { it.source }) {
            val length = entry.source - lastEnd - 1

            // Next range starts where the last one ends, don't need to add a pseudo-entry
            if (length == 0L) {
                continue
            }

            pe.add(Range(lastEnd, lastEnd, length))  // Map this entry to itself

            lastEnd = entry.source + entry.length + 1
        }

        pe.add(Range(lastEnd, lastEnd, Long.MAX_VALUE-lastEnd-1))

        pe
    }

    val withPseudoEntries by lazy { entries + pseudoEntries }

    fun map(x: Long) = entries.firstOrNull {it.maps(x)}?.map(x) ?: x
}


val day5 = Day(2023, 5) {
    val input by fetchInput()


    // === Puzzle one === //
    puzzle {
        val sections = input.split("\n\n")

        val seeds = sections.first()

        val mappings = sections.drop(1)
            .map {
                Mapping.fromString(it.split("\n", limit=2)[1])
            }

        seeds.split(" ")
            .drop(1)
            .filter(String::isNotEmpty)
            .map(String::toLong)
            .map {
                mappings.fold(it) { acc, map ->
                    map.map(acc)
                }
            }.min().toString()
    }


    fun minInRange(mappings: List<Mapping>, lower: Long, upper: Long): Long {
        if (mappings.isEmpty()) {
            return lower
        }

        val m = mappings.first()

        // Find intersections between this range and the entries in the map
        val entries: MutableList<Pair<Long, Long>> = mutableListOf()

        for (entry in m.withPseudoEntries) {
            val lowerIntersection = max(lower, entry.source)
            val upperIntersection = min(upper, entry.source + entry.length)

            if (lowerIntersection in lower..upperIntersection && upperIntersection <= upper) {
                entries.add(Pair(lowerIntersection-entry.source + entry.dest, upperIntersection-entry.source + entry.dest))
            }
        }

        // Map each range and find the minimum
        return entries.minOf {
            val l = minInRange(mappings.drop(1), it.first, it.second)
            l
        }
    }


    // === Part two === //
    puzzle {
        val sections = input.split("\n\n")

        val seeds = sections.first()

        val mappings = sections.drop(1)
            .map {
                Mapping.fromString(it.split("\n", limit=2)[1])
            }

        seeds.split(" ")
            .drop(1)
            .filter(String::isNotEmpty)
            .map(String::toLong)
            .windowed(2, 2)
            .map {
                minInRange(mappings, it[0], it[0]+it[1]-1)
            }.min().toString()
    }
}