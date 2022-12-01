package com.aleksandarpetrovski.aoc2022.day01

import java.nio.file.Paths

fun main() {
    val sortedElfCalories = Paths.get("2022-kotlin/src/com/aleksandarpetrovski/aoc2022/day01/input.in").toFile().readLines()
        .joinToString(",")
        .split(",,").map {elfCalories ->
            elfCalories.split(",").sumOf { it.toInt() }
        }.sortedDescending()

    val result1 = sortedElfCalories.first()
    println("EXPECTED RESULT 1: 65912")
    println("RESULT 1: $result1")

    val result2 = sortedElfCalories.subList(0, 3).sum()
    println("EXPECTED RESULT 2: 195625")
    println("RESULT 2: $result2")
}
