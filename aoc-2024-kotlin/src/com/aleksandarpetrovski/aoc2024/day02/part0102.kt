package com.aleksandarpetrovski.aoc2024.day02

import com.aleksandarpetrovski.aoc2024.readInputLines


fun main() {
    val records = readInputLines(day = 2, isTest = false)
        .map { line -> line.split(" ").map { it.toInt() } }

    val resultPart1 = records.count {
        isIncreasing(it) || isIncreasing(it.reversed())
    }

    val resultPart2 = records.count {
        isIncreasingWithError(it) || isIncreasingWithError(it.reversed())
    }

    println("Result part 1: $resultPart1")
    println("Result part 2: $resultPart2")
}

fun isIncreasingWithError(input: List<Int>): Boolean {
    if (isIncreasing(input)) return true
    for (i in input.indices) {
        val listWithoutOneElement = mutableListOf<Int>().apply {
            this.addAll(input)
            this.removeAt(i)
        }
        if (isIncreasing(listWithoutOneElement)) return true
    }
    return false
}

fun isIncreasing(list: List<Int>) = (1 until list.size)
    .map { index -> list[index] - list[index - 1] }
    .all { it in 1..3 }