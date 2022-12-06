package com.aleksandarpetrovski.aoc2022.day06

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val inputStream = readInputLines(day = 6)[0]
    println("Result Part 1: ${inputStream.firstDiverseWindow(4)}")
    println("Result Part 2: ${inputStream.firstDiverseWindow(14)}")
}

private fun String.firstDiverseWindow(windowSize: Int) = (windowSize until this.length).first { windowEnd ->
    (windowEnd - windowSize until windowEnd).distinctBy { this[it] }.count() == windowSize
}
