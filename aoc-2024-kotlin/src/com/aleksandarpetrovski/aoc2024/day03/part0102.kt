package com.aleksandarpetrovski.aoc2024.day03

import com.aleksandarpetrovski.aoc2024.readInputLines

val mulRegex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)".toRegex()
const val ENABLER = "do()"
const val DISABLER = "don't()"

fun main() {
    val memory = readInputLines(day = 3, isTest = false).joinToString()

    val resultPart1 = sumAllMultiplications(memory)
    val resultPart2 = sumAllMultiplications(getEnabledInstructions(memory))

    println("Result part 1: $resultPart1")
    println("Result part 2: $resultPart2")
}

fun sumAllMultiplications(input: String) = mulRegex.findAll(input).asIterable()
    .sumOf { multiplication -> evaluateMultiplication(multiplication.value) }

fun evaluateMultiplication(multiplication: String) = multiplication
    .removeSurrounding("mul(", ")")
    .split(",")
    .map { it.toInt() }
    .reduce { product, multiplier -> product * multiplier }

fun getEnabledInstructions(input: String): String {
    val disabledInstructions = input.substringAfter(DISABLER).split(DISABLER)
    val enabledInstructions = disabledInstructions.map {
        it.substringAfter(ENABLER, "")
    }
    return input.substringBefore(DISABLER, "") + enabledInstructions.joinToString()
}