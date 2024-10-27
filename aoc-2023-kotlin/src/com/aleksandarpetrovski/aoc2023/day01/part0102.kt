package com.aleksandarpetrovski.aoc2023.day01

import com.aleksandarpetrovski.aoc2023.readInputLines

val numStringsToDigits = mapOf(
    "0" to 0,
    "1" to 1,
    "2" to 2,
    "3" to 3,
    "4" to 4,
    "5" to 5,
    "6" to 6,
    "7" to 7,
    "8" to 8,
    "9" to 9,
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun main() {
    val inputLines = readInputLines(day = 1, isTest = false)

    val resultPart1 = inputLines.sumOf { line ->
        line.first { it.isDigit() }.digitToInt() * 10 + line.last { it.isDigit() }.digitToInt()
    }

    val resultPart2 = inputLines.sumOf { line ->
        val firstDigit = numStringsToDigits.keys.map { numString -> numString to line.indexOf(numString) }
            .filter { numStringToIndex -> numStringToIndex.second >= 0 }
            .minBy { numStringToIndex -> numStringToIndex.second }
            .first.let { numberString -> numStringsToDigits[numberString] }!!
        val lastDigit = numStringsToDigits.keys.map { numString -> numString to line.lastIndexOf(numString) }
            .filter { numStringToIndex -> numStringToIndex.second >= 0 }
            .maxBy { numStringToIndex -> numStringToIndex.second }
            .first.let { numberString -> numStringsToDigits[numberString] }!!
        firstDigit * 10 + lastDigit
    }

    println("Result part 1: $resultPart1")
    println("Result part 2: $resultPart2")
}