package com.aleksandarpetrovski.aoc2021.day11

import java.nio.file.Paths

fun main() {
    val octopusMatrix = Paths.get("2021-kotlin/src/day11/input.in").toFile().readLines().map { line ->
        line.toCharArray().map { it.toString().toInt() }.toIntArray()
    }.toTypedArray()

    val resultPart1 = (1..100).sumOf { flashStep(octopusMatrix) }
    val resultPart2 = (101 until Int.MAX_VALUE).firstOrNull { flashStep(octopusMatrix) == 100 }

    println("RESULT PART 1: $resultPart1")
    println("RESULT PART 2: $resultPart2")
}

fun flashStep(octopusMatrix: Array<IntArray>): Int {
    // increase all by 1
    octopusMatrix.indices.forEach { x ->
        octopusMatrix[x].indices.forEach { y ->
            octopusMatrix[x][y]++
        }
    }

    // flash if energy is full
    octopusMatrix.indices.forEach { x ->
        octopusMatrix[x].indices.forEach { y ->
            flashIfEnergyFull(octopusMatrix, x, y)
        }
    }

    // count flashes
    return octopusMatrix.sumOf { row -> row.count { it == 0 } }
}

fun flashIfEnergyFull(octopusMatrix: Array<IntArray>, x: Int, y: Int) {
    if (octopusMatrix[x][y] < 10) {
        return
    }
    octopusMatrix[x][y] = 0

    (x - 1..x + 1).forEach { i ->
        (y - 1..y + 1).filter { j -> positionExists(octopusMatrix, i, j) }.forEach { j ->
            if (octopusMatrix[i][j] != 0) {
                octopusMatrix[i][j]++
                flashIfEnergyFull(octopusMatrix, i, j)
            }
        }
    }
}

fun positionExists(octopusMatrix: Array<IntArray>, x: Int, y: Int) =
    octopusMatrix.indices.contains(x) && octopusMatrix[x].indices.contains(y)
