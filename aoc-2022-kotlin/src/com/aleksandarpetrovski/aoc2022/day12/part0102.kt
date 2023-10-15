package com.aleksandarpetrovski.aoc2022.day12

import com.aleksandarpetrovski.aoc2022.readInputLines

data class Position(val x: Int, val y: Int)

fun main() {
    val matrix = readInputLines(day = 12, isTest = false)
        .map { it.toCharArray() }.toTypedArray()

    val startPosition = matrix.getPositionsOf('S').first.also { matrix[it.x][it.y] = 'a' }
    val endPosition = matrix.getPositionsOf('E').first.also { matrix[it.x][it.y] = 'z' }

    val resultPart1 = findShortest(matrix, endPosition, matrix.newDistanceMatrix(), startPosition, 0)
    println("Result Part 1: $resultPart1")

    val resultPart2 = matrix.getPositionsOf('a')
        .minOfOrNull { findShortest(matrix, endPosition, matrix.newDistanceMatrix(), it, 0) }
    println("Result Part 2: $resultPart2")
}

private fun findShortest(
    matrix: Array<CharArray>,
    endPosition: Position,
    distanceTo: List<IntArray>,
    position: Position,
    stepsTravelled: Int
): Int {
    if (distanceTo[position.x][position.y] <= stepsTravelled) {
        return Int.MAX_VALUE
    }
    distanceTo[position.x][position.y] = stepsTravelled

    if (position == endPosition) {
        return stepsTravelled
    }

    val possibleNextMoves = listOf(
        Position(position.x - 1, position.y),
        Position(position.x + 1, position.y),
        Position(position.x, position.y - 1),
        Position(position.x, position.y + 1)
    ).filter { isMovePossible(matrix, Position(position.x, position.y), it) }

    return possibleNextMoves
        .minOfOrNull { findShortest(matrix, endPosition, distanceTo, it, stepsTravelled + 1) } ?: Int.MAX_VALUE
}

private fun isMovePossible(
    matrix: Array<CharArray>,
    oldPosition: Position,
    newPosition: Position,
): Boolean {
    // first check that the coordinates are in the valid range and that the climb is at most 1
    return newPosition.x in matrix.indices && newPosition.y in matrix[newPosition.x].indices
            && matrix[newPosition.x][newPosition.y] - matrix[oldPosition.x][oldPosition.y] <= 1
}

private fun Array<CharArray>.getPositionsOf(input: Char) = this.mapIndexed { indexX, chars ->
    chars.mapIndexed { indexY, char -> if (char == input) Position(indexX, indexY) else null }.filterNotNull()
}.flatten()

private fun Array<CharArray>.newDistanceMatrix(): List<IntArray> = this.map { it.map { Int.MAX_VALUE }.toIntArray() }