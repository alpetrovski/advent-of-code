package com.aleksandarpetrovski.aoc2022.day12

import com.aleksandarpetrovski.aoc2022.readInputLines

data class Position(val x: Int, val y: Int)

fun main() {
    val matrix = readInputLines(day = 12, isTest = false).map { it.toCharArray() }

    val start = matrix.getPositionsOf('S').first().also { matrix[it.x][it.y] = 'a' }
    val finish = matrix.getPositionsOf('E').first().also { matrix[it.x][it.y] = 'z' }

    val resultPart1 = findShortestPath(matrix, matrix.newDistances(), 0, start, finish)
    println("Result Part 1: $resultPart1")

    val resultPart2 = matrix.getPositionsOf('a')
        .minOfOrNull { possibleStart -> findShortestPath(matrix, matrix.newDistances(), 0, possibleStart, finish) }
    println("Result Part 2: $resultPart2")
}

private fun findShortestPath(
    matrix: List<CharArray>,
    distanceTo: List<IntArray>,
    stepsTravelled: Int,
    current: Position,
    finish: Position,
): Int {
    if (distanceTo[current.x][current.y] <= stepsTravelled) {
        return Int.MAX_VALUE
    }
    distanceTo[current.x][current.y] = stepsTravelled

    if (current == finish) {
        return stepsTravelled
    }

    val possibleNextMoves = listOf(
        Position(current.x - 1, current.y),
        Position(current.x + 1, current.y),
        Position(current.x, current.y - 1),
        Position(current.x, current.y + 1)
    ).filter { isValidMove(matrix, current, it) }

    return possibleNextMoves
        .minOfOrNull {findShortestPath(matrix, distanceTo, stepsTravelled + 1, it, finish) } ?: Int.MAX_VALUE
}

private fun isValidMove(
    matrix: List<CharArray>,
    oldPosition: Position,
    newPosition: Position,
): Boolean {
    // first check that the coordinates are in the valid range and that the climb is at most 1
    return newPosition.x in matrix.indices && newPosition.y in matrix[newPosition.x].indices
            && matrix[newPosition.x][newPosition.y] - matrix[oldPosition.x][oldPosition.y] <= 1
}

private fun List<CharArray>.getPositionsOf(input: Char) = this.mapIndexed { indexX, chars ->
    chars.mapIndexed { indexY, char -> if (char == input) Position(indexX, indexY) else null }.filterNotNull()
}.flatten()

private fun List<CharArray>.newDistances(): List<IntArray> = this.map { it.map { Int.MAX_VALUE }.toIntArray() }