import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


const val BENCHMARK_RERUNS = 16


fun listOfPuzzles(days: List<List<Day>>) = days.flatten().flatMap {
    it.puzzleSolutions
}


@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    val remainingArgs = args.toMutableList()

    // Basic argument parsing. Not really worth using a library
    val benchmark = remainingArgs.contains("-b")
    if (benchmark) {
        remainingArgs.remove("-b")
    }

    val list = remainingArgs.contains("-l")
    if (list) {
        remainingArgs.remove("-l")
    }

    if (list && benchmark) {
        println("Warning: list (-l) overrides benchmark (-b)")
    }

    // Collect all puzzles requested
    val allPuzzles = listOfPuzzles(listOf(
        Y2023.Y2023
    ))

    val (year, day, puzzle) = try {
        val (year, day, puzzle) = remainingArgs.single().split("/")
        Triple(year, day, puzzle)
    } catch (e: Throwable) {
        println("Please give the puzzle to run in the form year/day/puzzle")
        return
    }

    // Filter puzzles to match args
    val puzzles = allPuzzles.filter {
        (year == "*" || it.day.year.toString() == year)
                && (day == "*" || it.day.day.toString() == day)
                && (puzzle == "*" || it.partNumber.toString() == puzzle)
    }

    // Run the puzzles and print results (& benchmark times)
    for (p in puzzles) {
        if (list) {
            println("${p.day.year}/${p.day.day}/${p.partNumber}")
        } else {
            println("=== Running year ${p.day.year}, day ${p.day.day}, puzzle ${p.partNumber} ===")
            println(p.run())

            if (benchmark) {
                val time = measureTime {
                    repeat(BENCHMARK_RERUNS) {
                        p.run()
                    }
                }

                println("Took ${time.inWholeMilliseconds}mS / $BENCHMARK_RERUNS = ${time.inWholeMilliseconds / BENCHMARK_RERUNS} milliseconds per run")
            }

            println()
        }
    }

    // Warn the user if no puzzles were run
    if (puzzles.isEmpty()) {
        println("No puzzles match $year/$day/$puzzle")
        println("Use -l /*/*/* to list all puzzles")
    }
}