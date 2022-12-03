package com.aleksandarpetrovski.aoc2022.day03

import java.lang.IllegalArgumentException
import java.nio.file.Paths

private const val INPUT_FILE = "2022-kotlin/src/com/aleksandarpetrovski/aoc2022/day03/input.in"

private fun findFirstCommonChar(inputs: List<String>) =
    inputs.map { it.toSet() }.reduce { acc, chars -> acc.intersect(chars) }.first()

private fun itemPriority(input: Char) = when (input) {
    in 'a'..'z' -> input - 'a' + 1
    in 'A'..'Z' -> input - 'A' + 27
    else -> throw IllegalArgumentException()
}

fun main() {
    val inputLines = Paths.get(INPUT_FILE).toFile().readLines()
    val result1 = inputLines.sumOf { rucksack ->
        val middleOfItemsStream = rucksack.length / 2
        val compartmentA = rucksack.substring(0, middleOfItemsStream)
        val compartmentB = rucksack.substring(middleOfItemsStream)
        val commonItem = findFirstCommonChar(listOf(compartmentA, compartmentB))
        itemPriority(commonItem)
    }

    val result2 = inputLines.chunked(3)
        .sumOf { elfGroup -> itemPriority(findFirstCommonChar(elfGroup)) }

    println("Result 1: $result1")
    println("Result 2: $result2")
}

