package com.aleksandarpetrovski.aoc2022.day01

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val sortedElfCalories = readInputLines(1)
        .joinToString(",").split(",,")
        .map {elfCalories ->
            elfCalories.split(",").sumOf { it.toInt() }
        }.sortedDescending()

    val result1 = sortedElfCalories.first()
    println("RESULT 1: $result1")

    val result2 = sortedElfCalories.subList(0, 3).sum()
    println("RESULT 2: $result2")
}
