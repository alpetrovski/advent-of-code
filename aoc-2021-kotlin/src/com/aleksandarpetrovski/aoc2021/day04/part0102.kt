package com.aleksandarpetrovski.aoc2021.day04

import java.nio.file.Paths

fun main() {
    val input = Paths.get("2021-kotlin/src/day04/input.in").toFile().readLines()
    val numbers = input[0].split(",").map { it.toInt() }

    val sortedWinningCards = (2 until input.size step 6).map { cardStart ->
        val cardNumbers = (0 until 5).map { cardLine ->
            input[cardStart + cardLine].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toIntArray()
        }.toTypedArray()
        BingoCard(cardNumbers, numbers)
    }.sortedBy { it.bingoAfter }

    val firstWinningCard = sortedWinningCards.first()
    val lastWinningCard = sortedWinningCards.last()

    println("RESULT part 01: ${firstWinningCard.leftoverSum * firstWinningCard.winningNumber}")
    println("RESULT part 02: ${lastWinningCard.leftoverSum * lastWinningCard.winningNumber}")
}

class BingoCard(
    rows: Array<IntArray>,
    numbers: List<Int>
) {
    val bingoAfter: Int
    val leftoverSum: Int
    val winningNumber: Int

    init {
        var winningNumber = -1
        var bingoAfter = 0

        for (number in numbers) {
            drawNumber(rows, number)
            bingoAfter++
            if (isBingo(rows)) {
                winningNumber = number
                break
            }
        }

        this.bingoAfter = bingoAfter
        this.winningNumber = winningNumber
        this.leftoverSum = rows.flatMap { it.asIterable() }.filter { it != -1 }.sum()
    }

    private fun drawNumber(rows: Array<IntArray>, number: Int) {
        for (row in rows.indices) {
            for (column in rows[row].indices) {
                if (rows[row][column] == number) {
                    rows[row][column] = -1
                }
            }
        }
    }

    private fun isBingo(rows: Array<IntArray>): Boolean {
        return rows.any { row -> row.all { column -> column == -1 } }
                || (0 until 5).any { column -> rows.all { row -> row[column] == -1 } }
    }
}
