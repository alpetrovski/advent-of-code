package com.aleksandarpetrovski.aoc2022.day01

import java.nio.file.Paths

private const val INPUT_FILE = "2022-kotlin/src/com/aleksandarpetrovski/aoc2022/day01/input.in"

fun main() {
    val sortedElfCalories = Paths.get(INPUT_FILE).toFile().readLines()
        .joinToString(",").split(",,")
        .map {elfCalories ->
            elfCalories.split(",").sumOf { it.toInt() }
        }.sortedDescending()

    val result1 = sortedElfCalories.first()
    println("RESULT 1: $result1")

    val result2 = sortedElfCalories.subList(0, 3).sum()
    println("RESULT 2: $result2")
}
