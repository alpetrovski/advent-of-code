package day01

import java.nio.file.Paths

fun main() {
    val numbers = Paths.get("2021-kotlin/src/day01/input.in").toFile().readLines().map { it.toInt() }

    val result = (1 until numbers.size).count {
        numbers[it] > numbers[it - 1]
    }

    println("RESULT: $result")
}
