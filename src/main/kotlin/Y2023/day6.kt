package Y2023

import Day
import java.math.BigInteger
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

val day6 = Day(2023, 6) {
    val input by fetchInput()


    // Derived from the equation y=-x^{2}+Tx
    // Where x is the time that the button is held for and T is the duration of the race
    // y-W where W is the winning time gives roots at possible durations to hold the button to match W
    // Finding the difference between these roots (rounded appropriately) gives the number of ways to beat or
    //  match the winning time
    // Offset W by half a millisecond to exclude matches
    fun waysToWin(time: BigInteger, winningDistance: BigInteger): BigInteger {
        val desc = sqrt((((time * time).toDouble() - (4f * (winningDistance.toDouble() + 0.5)))))

        val upper = floor((time.toDouble() + desc) / 2f).toInt()
        val lower = ceil((time.toDouble() - desc) / 2f).toInt()

        return upper.toBigInteger() - lower.toBigInteger() + 1.toBigInteger()
    }


    // === Part one === //
    puzzle {
        val times = input.lines().first()
            .split(":")[1]
            .split("\\s+".toRegex())
            .filter(String::isNotEmpty)
            .map(String::toBigInteger)

        val distances = input.lines()[1]
            .split(":")[1]
            .split("\\s+".toRegex())
            .filter(String::isNotEmpty)
            .map(String::toBigInteger)

        times.zip(distances)
            .map { waysToWin(it.first, it.second) }
            .fold(1.toBigInteger(), BigInteger::times).toString()
    }

    // === Part two === //
    puzzle {
        val time = input.lines().first()
            .split(":")[1]
            .split("\\s+".toRegex())
            .joinToString("")
            .toBigInteger()

        val distance = input.lines()[1]
            .split(":")[1]
            .split("\\s+".toRegex())
            .joinToString("")
            .toBigInteger()

        waysToWin(time, distance).toString()
    }
}