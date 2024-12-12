package com.aleksandarpetrovski.aoc2024.day03

import com.aleksandarpetrovski.aoc2024.readInputLines

val mulRegex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)".toRegex()

fun main() {
    val inputLines = readInputLines(day = 3, isTest = false)

    val resultPart1 = inputLines.sumOf { input -> sumAllMultiplications(input) }
    val resultPart2 = inputLines.sumOf { input -> enabledMultiplications(input).sumOf { sumAllMultiplications(it)} }

    println("Result part 1: $resultPart1")
    println("Result part 2: $resultPart2")
}

fun sumAllMultiplications(input: String) = mulRegex.findAll(input).asIterable().sumOf {
    val multipliers = it.value.removePrefix("mul(").removeSuffix(")").split(",")
    multipliers[0].toLong() * multipliers[1].toLong()
}

fun enabledMultiplications(input: String): List<String> {
    val splitByDont = input.split("don't()")
    val splitByDo = splitByDont.drop(1).map {
        val temp = it.split("do()")
        if (temp.size > 1) temp[1] else ""
    }
    return mutableListOf<String>().apply {
        add(splitByDont[0])
        addAll(splitByDo)
    }
}