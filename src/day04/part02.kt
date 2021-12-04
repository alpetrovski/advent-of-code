package day04

import java.nio.file.Paths

fun main() {
    val input = Paths.get("src/day04/input.in").toFile().readLines()
    val numbers = input[0].split(",").map { it.toInt() }

    var cards = (2 until input.size step 6).map { line ->
        val cardInput = (0 until 5).map { subline ->
            input[line + subline].split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toIntArray()
        }.toTypedArray()
        Card(cardInput)
    }

    var lastToWin: Card? = null
    var result = 0
    for (number in numbers) {
        cards.forEach { it.drawNumber(number) }
        cards = cards.filter { it.isBingo().not() }

        if (cards.count{it.isBingo().not()} == 1) {
            lastToWin = cards.first{it.isBingo().not()}
        }

        if (cards.isEmpty()) {
            result = lastToWin!!.getSumOfLeftNumbers() * number
            break
        }
    }

    println("RESULT: $result")
}