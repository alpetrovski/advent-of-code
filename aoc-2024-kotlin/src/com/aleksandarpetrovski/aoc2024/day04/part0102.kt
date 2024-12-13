package com.aleksandarpetrovski.aoc2024.day04

import com.aleksandarpetrovski.aoc2024.readInputLines

fun main() {
    val matrix = readInputLines(day = 4, isTest = false).map { it.toCharArray() }

    val resultPart1 = matrix.indices.sumOf { i ->
        matrix[i].indices.sumOf { j -> getAllWordsFromPosition(matrix, i, j).count { it == "XMAS" } }
    }

    val resultPart2 = matrix.indices.sumOf { i ->
        matrix[i].indices.filter { j -> matrix.getChar(i, j) == "A" }.count { j -> isXmas(matrix, i, j) }
    }

    println("Result part 1: $resultPart1")
    println("Result part 2: $resultPart2")
}

fun isXmas(m: List<CharArray>, x: Int, y: Int): Boolean {
    val diagonal1 = m.getChar(x - 1, y - 1) + m.getChar(x + 1, y + 1)
    val diagonal2 = m.getChar(x - 1, y + 1) + m.getChar(x + 1, y - 1)
    return (diagonal1 == "MS" || diagonal1 == "SM") && (diagonal2 == "MS" || diagonal2 == "SM")
}

fun getAllWordsFromPosition(m: List<CharArray>, x: Int, y: Int): List<String> = listOf(
    // horizontal
    m.getChar(x, y) + m.getChar(x + 1, y) + m.getChar(x + 2, y) + m.getChar(x + 3, y),
    // horizontal backwards
    m.getChar(x, y) + m.getChar(x - 1, y) + m.getChar(x - 2, y) + m.getChar(x - 3, y),
    // vertical
    m.getChar(x, y) + m.getChar(x, y + 1) + m.getChar(x, y + 2) + m.getChar(x, y + 3),
    // vertical backwards
    m.getChar(x, y) + m.getChar(x, y - 1) + m.getChar(x, y - 2) + m.getChar(x, y - 3),
    // diagonal 1
    m.getChar(x, y) + m.getChar(x + 1, y - 1) + m.getChar(x + 2, y - 2) + m.getChar(x + 3, y - 3),
    // diagonal 2
    m.getChar(x, y) + m.getChar(x + 1, y + 1) + m.getChar(x + 2, y + 2) + m.getChar(x + 3, y + 3),
    // diagonal 3
    m.getChar(x, y) + m.getChar(x - 1, y + 1) + m.getChar(x - 2, y + 2) + m.getChar(x - 3, y + 3),
    // diagonal 4
    m.getChar(x, y) + m.getChar(x - 1, y - 1) + m.getChar(x - 2, y - 2) + m.getChar(x - 3, y - 3),
)

fun List<CharArray>.getChar(x: Int, y: Int) = if (x in indices && y in this[x].indices) {
    this[x][y].toString()
} else {
    ""
}