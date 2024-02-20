package com.aleksandarpetrovski.aoc2020.day01

import java.nio.file.Paths

fun main() {
    val numbers = Paths.get("2020-kotlin/src/day01/input.in").toFile().readLines().map { it.toLong() }

    for (i in 0 until numbers.size - 2) {
        for (j in i + 1 until numbers.size - 1) {
            for (k in j + 1 until numbers.size) {
                if (2020L == numbers[i] + numbers[j] + numbers[k]) {
                    println(numbers[i] * numbers[j] * numbers[k])
                    return
                }
            }
        }
    }
}
