package com.aleksandarpetrovski.aoc2022.day06

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val inputStream = readInputLines(day = 6)[0]
    println("Result Part 1: ${inputStream.firstNDistinctChars(4)}")
    println("Result Part 2: ${inputStream.firstNDistinctChars(14)}")
}

fun String.firstNDistinctChars(differentChars: Int) = (differentChars until this.length).first { windowEnd ->
    (windowEnd - differentChars until windowEnd).distinctBy { this[it] }.count() == differentChars
}
