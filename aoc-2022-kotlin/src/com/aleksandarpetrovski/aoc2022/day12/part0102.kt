package com.aleksandarpetrovski.aoc2022.day12

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val matrix = readInputLines(day = 12, isTest = false)
        .map { it.toCharArray() }.toTypedArray()

    val (startX, startY) = matrix.getCoordinatesOf('S').first
    matrix[startX][startY] = 'a'

    val resultPart1 = findShortest(matrix, matrix.newDistanceMatrix(), startX, startY, 0)
    println("Result Part 1: $resultPart1")

    val resultPart2 = matrix.getCoordinatesOf('a')
        .minOfOrNull { findShortest(matrix, matrix.newDistanceMatrix(), it.first, it.second, 0) }
    println("Result Part 2: $resultPart2")
}

private fun findShortest(
    matrix: Array<CharArray>,
    distanceTo: List<IntArray>,
    posX: Int, posY: Int,
    stepsTravelled: Int
): Int {
    if (distanceTo[posX][posY] <= stepsTravelled) {
        return Int.MAX_VALUE
    }
    distanceTo[posX][posY] = stepsTravelled

    if (matrix[posX][posY] == 'E') {
        return stepsTravelled
    }

    val possibleNextMoves = listOf(
        Pair(posX - 1, posY),
        Pair(posX + 1, posY),
        Pair(posX, posY - 1),
        Pair(posX, posY + 1)
    ).filter { isMovePossible(matrix, Pair(posX, posY), it) }

    return possibleNextMoves
        .minOfOrNull { findShortest(matrix, distanceTo, it.first, it.second, stepsTravelled + 1) } ?: Int.MAX_VALUE
}

private fun isMovePossible(
    matrix: Array<CharArray>,
    old: Pair<Int, Int>,
    new: Pair<Int, Int>,
): Boolean {
    return new.first in matrix.indices && new.second in matrix[new.first].indices
            && ((matrix[new.first][new.second] == 'E' && matrix[old.first][old.second] in 'y'..'z') || (matrix[new.first][new.second] != 'E' && matrix[new.first][new.second] - matrix[old.first][old.second] <= 1))
}

private fun Array<CharArray>.getCoordinatesOf(input: Char) = this.mapIndexed { indexX, chars ->
    chars.mapIndexed { indexY, char -> if (char == input) Pair(indexX, indexY) else null }.filterNotNull()
}.flatten()

private fun Array<CharArray>.newDistanceMatrix(): List<IntArray> = this.map { it.map { Int.MAX_VALUE }.toIntArray() }