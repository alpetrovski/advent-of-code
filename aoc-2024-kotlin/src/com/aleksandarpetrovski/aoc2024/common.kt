package com.aleksandarpetrovski.aoc2024

import java.nio.file.Paths

fun readInputLines(day: Int, isTest: Boolean = false): List<String> {
    val paddedDay = day.toString().padStart(2, '0')
    val fileName = if (isTest) "test-input.in" else "input.in"
    return Paths.get("aoc-2024-kotlin/src/com/aleksandarpetrovski/aoc2024/day${paddedDay}/${fileName}")
        .toFile().readLines()
}