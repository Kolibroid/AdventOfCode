class Puzzle(val day: Day, val partNumber: Int, private val body: () -> String) {
    fun run() = body()
}

class Day(val year: Int, val day: Int, puzzles: Day.() -> Unit) {
    private var solutions: MutableList<Puzzle> = mutableListOf()
    val puzzleSolutions: List<Puzzle>
        get() = solutions

    init {
        puzzles()
    }

    fun puzzle(body: () -> String) {
        solutions.add(Puzzle(this, solutions.size, body))
    }

    fun fetchInput() = lazy {
        fetch(year, "${day}.txt")
    }
}
