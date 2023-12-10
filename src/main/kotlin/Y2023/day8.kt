package Y2023

import Day


data class Node(val left: String, val right: String)

val day8 = Day(2023, 8) {
    val input by fetchInput()


    // Yields each node in the path that you take based on the given directions
    fun tracePath(start: String, network: Map<String, Node>, direction: String) = sequence {
        // Loop direction infinitely
        val directions: Sequence<Char> = sequence {
            while (true) {
                for (char in direction) {
                    yield(char)
                }
            }
        }

        // Step through each node and yield it
        var current = start
        for (d: Char in directions) {
            current = if (d == 'L') {
                network[current]!!.left
            } else {
                network[current]!!.right
            }

            yield(current)
        }
    }

    // === Part one === //
    puzzle {
        val (directionString, networkString) = input.split("\n\n", limit=2)

        val network: MutableMap<String, Node> = mutableMapOf()

        // Names always appear in the same place in each string, just extract using substring
        for (line in networkString.lines()) {
            val name = line.substring(0..2)
            val left = line.substring(7..9)
            val right = line.substring(12..14)

            network[name] = Node(left, right)
        }

        (tracePath("AAA", network, directionString).takeWhile {
            it != "ZZZ"
        }.count()+1).toString()
    }


    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) {
            return a
        }

        return gcd(b, (a % b))
    }


    // === Part two === //
    puzzle {
        val (directionString, networkString) = input.split("\n\n", limit=2)

        val network: MutableMap<String, Node> = mutableMapOf()

        // Names always appear in the same place in each string, just extract using substring
        for (line in networkString.lines()) {
            val name = line.substring(0..2)
            val left = line.substring(7..9)
            val right = line.substring(12..14)

            network[name] = Node(left, right)
        }


        // Note that this is only necessarily correct if there is only one end on each path

        // Find minimum steps to reach the end
        val lengths: MutableList<Long> = mutableListOf()
        for (start in network.keys.filter { it.endsWith("A") }) {
            lengths.add(tracePath(start, network, directionString).takeWhile {
                !it.endsWith("Z")
            }.count().toLong()+1)
        }

        // Find the LCM of these step counts
        lengths.fold(1L) {acc, x ->
            (acc*x) / gcd(acc, x)
        }.toString()
    }
}