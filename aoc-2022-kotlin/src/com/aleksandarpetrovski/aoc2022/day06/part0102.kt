package com.aleksandarpetrovski.aoc2022.day06

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val inputStream = readInputLines(day = 6)[0]
    println("Result Part 1: ${inputStream.firstDiverseWindow(windowLength = 4)}")
    println("Result Part 2: ${inputStream.firstDiverseWindow(windowLength = 14)}")
}

private fun String.firstDiverseWindow(windowLength: Int) = (windowLength until this.length).first { windowEnd ->
    (windowEnd - windowLength until windowEnd).distinctBy { this[it] }.count() == windowLength
}
