package com.aleksandarpetrovski.aoc2022.day06

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val input = readInputLines(day = 6)[0]
    println("Result Part 1: ${firstNDistinctChars(input, 4)}")
    println("Result Part 2: ${firstNDistinctChars(input, 14)}")
}

fun firstNDistinctChars(input: String, distinctChars: Int) = (distinctChars until input.length).first { index ->
    input.substring(index - distinctChars, index).toSet().size == distinctChars
}
