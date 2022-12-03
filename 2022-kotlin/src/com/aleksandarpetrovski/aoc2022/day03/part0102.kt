package com.aleksandarpetrovski.aoc2022.day03

import java.lang.IllegalArgumentException
import java.nio.file.Paths

private const val INPUT_FILE = "2022-kotlin/src/com/aleksandarpetrovski/aoc2022/day03/input.in"

private fun findFirstCommonItem(inputs: List<String>) =
    inputs.map { it.toSet() }.reduce { acc, chars -> acc.intersect(chars) }.first()

private fun itemPriority(input: Char) = when (input) {
    in 'a'..'z' -> input - 'a' + 1
    in 'A'..'Z' -> input - 'A' + 27
    else -> throw IllegalArgumentException()
}

fun main() {
    val inputLines = Paths.get(INPUT_FILE).toFile().readLines()
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

