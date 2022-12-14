package com.aleksandarpetrovski.aoc2022.day10

import com.aleksandarpetrovski.aoc2022.readInputLines
import kotlin.math.abs

fun main() {
    var register = 1
    var cycles = 0

    val registerValueAtCycle = readInputLines(day = 10).map { line ->
        val cycleToValuePairs = mutableListOf(Pair(++cycles, register))
        if (line.startsWith("addx")) {
            cycleToValuePairs.add(Pair(++cycles, register))
            register += line.split(" ")[1].toInt()
        }
        cycleToValuePairs
    }.flatten()

    val resultPart1 = registerValueAtCycle
        .filter { (cycle, _) -> cycle % 40 == 20 }
        .sumOf { (cycle, register) -> cycle * register }

    val resultPart2 = registerValueAtCycle
        .map { (cycle, register) -> abs((cycle - 1) % 40 - register) <= 1 }
        .map { pixelDrawn -> if (pixelDrawn) '#' else '.' }

    println("Result Part 1: $resultPart1")
    println("Result Part 2:").also {
        resultPart2.chunked(40)
            .map { it.joinToString(" ") }
            .forEach(::println)
    }
}
