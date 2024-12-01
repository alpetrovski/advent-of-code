package com.aleksandarpetrovski.aoc2024.day01

import com.aleksandarpetrovski.aoc2024.readInputLines
import kotlin.math.abs


fun main() {
    val inputLines = readInputLines(day = 1, isTest = false)
    val sortedListA = inputLines.map { it.split("   ")[0].toInt() }.sorted()
    val sortedListB = inputLines.map { it.split("   ")[1].toInt() }.sorted()

    val resultPart1 = sortedListA.mapIndexed { index, valueA -> abs(valueA - sortedListB[index]) }.sum()
    val resultPart2 = sortedListA.sumOf { valueFromA -> sortedListB.count { it == valueFromA } * valueFromA }

    println("Result part 1: $resultPart1")
    println("Result part 2: $resultPart2")
}