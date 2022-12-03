package com.aleksandarpetrovski.aoc2022.day03

import com.aleksandarpetrovski.aoc2022.readInputLines
import kotlin.IllegalArgumentException

private fun findFirstCommonItem(inputStrings: List<String>) = inputStrings
    .map(String::toSet)
    .reduce { commonChars, nextInputChars -> commonChars.intersect(nextInputChars) }
    .first()

private fun itemPriority(input: Char) = when (input) {
    in 'a'..'z' -> input - 'a' + 1
    in 'A'..'Z' -> input - 'A' + 27
    else -> throw IllegalArgumentException()
}

fun main() {
    val inputLines = readInputLines(3)
    val result1 = inputLines
        .map { rucksack -> rucksack.chunked(rucksack.length / 2) }
        .map(::findFirstCommonItem)
        .sumOf(::itemPriority)

    val result2 = inputLines.chunked(3)
        .map(::findFirstCommonItem)
        .sumOf(::itemPriority)

    println("Result 1: $result1")
    println("Result 2: $result2")
}

