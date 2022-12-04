package com.aleksandarpetrovski.aoc2022.day04

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val elfPairAssignments = readInputLines(4).map { pair ->
        val assignments = pair.split(",")
        val elf1Range = IntRange(assignments[0].split("-")[0].toInt(), assignments[0].split("-")[1].toInt())
        val elf2Range = IntRange(assignments[1].split("-")[0].toInt(), assignments[1].split("-")[1].toInt())
        Pair(elf1Range, elf2Range)
    }

    val resultPart1 = elfPairAssignments.count { (elf1Range, elf2Range) ->
        (elf1Range.first <= elf2Range.first && elf1Range.last >= elf2Range.last)
                || (elf2Range.first <= elf1Range.first && elf2Range.last >= elf1Range.last)
    }

    val resultPart2 = elfPairAssignments.count { (elf1Range, elf2Range) ->
        elf1Range.contains(elf2Range.first) || elf2Range.contains(elf1Range.first)
    }

    println("Result Part 1: $resultPart1")
    println("Result Part 2: $resultPart2")
}