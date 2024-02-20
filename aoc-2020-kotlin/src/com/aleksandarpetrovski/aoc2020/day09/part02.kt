package com.aleksandarpetrovski.aoc2020.day09

import java.nio.file.Paths
import java.util.*

fun main() {
    println(com.aleksandarpetrovski.aoc2020.day09.findWeakness("2020-kotlin/src/day09/input.in"))
}

private fun findWeakness(filePath: String): Long? {
    val numbers = Paths.get(filePath).toFile().readLines().map { it.toLong() }
    val invalidNumber = com.aleksandarpetrovski.aoc2020.day09.findInvalidNumber(numbers)!!
    val contiguousRange = LinkedList<Long>()

    numbers.forEach {number ->
        contiguousRange.addLast(number)

        while (contiguousRange.sum() > invalidNumber) {
            contiguousRange.removeFirst()
        }

        if (contiguousRange.sum() == invalidNumber) {
            return contiguousRange.minOrNull()!! + contiguousRange.maxOrNull()!!
        }
    }
    return null
}
