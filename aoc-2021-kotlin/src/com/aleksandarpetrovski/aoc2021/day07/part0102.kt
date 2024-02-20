package com.aleksandarpetrovski.aoc2021.day07

import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val crabPositions = Paths.get("2021-kotlin/src/day07/input.in").toFile().readLines()[0].split(",").map { it.toInt() }
    
    val minCrabPosition = crabPositions.minOrNull()!!
    val maxCrabPosition = crabPositions.maxOrNull()!!

    val resultPart1 = (minCrabPosition..maxCrabPosition)
        .minOfOrNull { position -> crabPositions.sumOf { crabPosition -> abs(position - crabPosition).toLong() }}!!

    val resultPart2 = (minCrabPosition..maxCrabPosition)
        .minOfOrNull { position -> crabPositions.sumOf { crabPosition -> (1 .. abs(position - crabPosition).toLong()).sum() }}!!

    println("RESULT part 01: $resultPart1")
    println("RESULT part 02: $resultPart2")
}
