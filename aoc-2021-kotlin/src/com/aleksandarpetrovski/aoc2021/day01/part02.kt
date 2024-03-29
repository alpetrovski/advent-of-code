package com.aleksandarpetrovski.aoc2021.day01

import java.nio.file.Paths

fun main() {
    val numbers = Paths.get("2021-kotlin/src/day01/input.in").toFile().readLines().map { it.toInt() }

    val result = (3 until numbers.size).count { rangeEnd ->
        (rangeEnd - 2..rangeEnd).sumOf { numbers[it] } >
                (rangeEnd - 3 until rangeEnd).sumOf { numbers[it] }
    }

    println("RESULT: $result")
}
