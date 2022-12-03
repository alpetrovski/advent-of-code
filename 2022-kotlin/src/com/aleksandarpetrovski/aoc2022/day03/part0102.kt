package com.aleksandarpetrovski.aoc2022.day03

import java.lang.IllegalArgumentException
import java.nio.file.Paths

private const val INPUT_FILE = "2022-kotlin/src/com/aleksandarpetrovski/aoc2022/day03/input.in"

private fun findFirstCommonChar(inputs: List<CharArray>) = inputs.first().first { itemToBeFound ->
    inputs.subList(1, inputs.size).all { it.contains(itemToBeFound) }
}

private fun itemPriority(input: Char) = when (input) {
    in 'a'..'z' -> input - 'a' + 1
    in 'A'..'Z' -> input - 'A' + 27
    else -> throw IllegalArgumentException()
}

fun main() {
    val inputLines = Paths.get(INPUT_FILE).toFile().readLines()
    val result1 = inputLines.sumOf { rucksack ->
        val itemsInRucksackCount = rucksack.length
        val compartmentA = rucksack.substring(0, (itemsInRucksackCount / 2))
        val compartmentB = rucksack.substring((itemsInRucksackCount / 2))
        val commonItem = findFirstCommonChar(listOf(compartmentA.toCharArray(), compartmentB.toCharArray()))
        itemPriority(commonItem)
    }

    val result2 = inputLines.withIndex()
        .groupBy { it.index / 3 }
        .map { it.value }
        .sumOf { elfGroup ->
            itemPriority(findFirstCommonChar(elfGroup.map { it.value.toCharArray() }))
        }

    println("Expected Result 1: 7990")
    println("Result 1: $result1")

    println("Expected Result 2: 2602")
    println("Result 2: $result2")
}

