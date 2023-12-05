package Y2023

import Day


const val DIGITS = "0123456789"
const val NOT_SYMBOLS = ".$DIGITS"


val day3 = Day(2023, 3) {
    val input by fetchInput()


    fun isValid(matrix: List<String>, col: Int, row: Int): Boolean {
        for (j in (-1..1)) {
            for (i in (-1..1)) {
                val symbol = matrix.getOrNull(row + j)?.getOrNull(col + i)
                    ?: continue
                if (symbol !in NOT_SYMBOLS) {
                    return true
                }
            }
        }

        return false
    }

    fun isStartOfValidNumber(matrix: List<String>, col: Int, row: Int): Boolean {
        if (matrix[row][col] !in DIGITS) {
            return false
        }

        if (col != 0 && matrix[row][col-1] in DIGITS) {
            return false
        }

        var lcol = col
        while (lcol < matrix[row].length && matrix[row][lcol] in DIGITS) {
            if (isValid(matrix, lcol, row)) {
                return true
            }

            lcol += 1
        }

        return false
    }


    fun getNumber(matrix: List<String>, col: Int, row: Int): String {
        var num = ""
        var lcol = col
        while (lcol < matrix[row].length && matrix[row][lcol] in DIGITS) {
            num += matrix[row][lcol]
            lcol += 1
        }

        return num
    }

    // Find the start of a number in the matrix when given the co-ordinates of a digit in the number
    fun numberStart(matrix: List<String>, col: Int, row: Int): Pair<Int, Int> {
        var lcol = col

        while (lcol >= 0 && matrix[row][lcol] in DIGITS) {
            lcol -= 1
        }

        return Pair(lcol+1, row)
    }


    // === Part 1 === //
    puzzle {
        val matrix = input.lines()

        var total = 0
        // Find each
        for (line in matrix.withIndex()) {
            var i = 0
            while (i<line.value.length) {
                if (isStartOfValidNumber(matrix, i, line.index)) {
                    val num = getNumber(matrix, i, line.index)
                    total += num.toInt()
                    i += num.length - 1
                }
                i += 1
            }
        }


        total.toString()
    }

    // === Part 2 === //
    puzzle {
        val matrix = input.lines()

        var total = 0

        // Find each asterisk, then scan all elements around it, and create a list where the numbers around them start
        for (line in matrix.withIndex()) {
            for (symbol in line.value.withIndex()) {
                if (symbol.value == '*') {
                    val starts: MutableSet<Pair<Int, Int>> = mutableSetOf()

                    // Scan neighbours
                    for (col in (-1..1)) {
                        for (row in (-1..1)) {
                            val rcol = symbol.index + col
                            val rrow = line.index + row

                            // If this element is within the matrix and is a digit then find the start of the number it's a part of
                            if (0 <= rrow && rrow < matrix.size && 0 <= rcol && rcol < matrix[rrow].length && matrix[rrow][rcol] in DIGITS) {
                                starts.add(numberStart(matrix, rcol, rrow))
                            }
                        }
                    }

                    if (starts.size == 2) {
                        total += starts.map {
                            getNumber(matrix, it.first, it.second).toInt()
                        }.fold(1, Int::times)
                    }
                }
            }
        }

        total.toString()
    }
}