package day04

import java.nio.file.Paths

fun main() {
    val input = Paths.get("src/day04/input.in").toFile().readLines()
    val numbers = input[0].split(",").map { it.toInt() }

    val cards = (2 until input.size step 6).map { line ->
        val cardInput = (0 until 5).map { subline -> input[line + subline].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toIntArray() }.toTypedArray()
        Card(cardInput)
    }

    var result = 0
    for (number in numbers) {
        cards.forEach { it.drawNumber(number) }
        if (cards.any { it.isBingo() }) {
            result = cards.first{it.isBingo()}.getSumOfLeftNumbers() * number
            break
        }
    }

    println("RESULT: $result")
}

class Card(
    val rows: Array<IntArray>
) {
    fun isBingo(): Boolean {
        return rows.any { row -> row.all { column -> column == -1 } }
                || (0 until 5).any { column -> rows.all { row -> row[column] == -1 } }
    }

    fun drawNumber(number: Int) {
        for (row in rows.indices) {
            for (column in rows[row].indices) {
                if (rows[row][column] == number) {
                    rows[row][column] = -1
                }
            }
        }
    }

    fun getSumOfLeftNumbers(): Int {
        return rows.flatMap { it.asIterable() }.filter { it != -1 }.sum()
    }
}