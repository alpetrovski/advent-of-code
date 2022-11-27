package day13

import java.nio.file.Paths
import kotlin.math.max

fun main() {
    val busses = Paths.get("src/day13/input.in").toFile().readLines()[1].split(",").map {
        if (it == "x") 1L else it.toLong()
    }.toTypedArray()

    var result = busses[0]
    var increment = result

    for (i in 1 until busses.size) {
        while (!isValidTimestamp(result = result, busses = busses, untilIndex = i)) {
            result = result.plus(increment)
        }

        // skip in the last cycle, not needed
        if (i != busses.size - 1) {
            increment = calculateCommonMultiply(increment, busses[i])
        }
    }

    println(result)
}

fun isValidTimestamp(result: Long, busses: Array<Long>, untilIndex: Int): Boolean {
    for (i in 0 .. untilIndex) {
        if ((result + i) % busses[i] != 0L) {
            return false
        }
    }
    return true
}

fun calculateCommonMultiply(a: Long, b: Long):Long {
    val bigger = max(a, b)
    var result = bigger
    while(result % a != 0L || result % b != 0L) {
        result += bigger
    }
    return result
}