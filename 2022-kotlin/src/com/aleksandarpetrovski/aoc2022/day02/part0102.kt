package com.aleksandarpetrovski.aoc2022.day02

import com.aleksandarpetrovski.aoc2022.day02.Action.*
import java.lang.IllegalArgumentException
import java.nio.file.Paths

private const val INPUT_FILE = "2022-kotlin/src/com/aleksandarpetrovski/aoc2022/day02/input.in"
private enum class Action(val score: Int, val representations: List<String>) {
    ROCK(1, listOf("A", "X")),
    PAPER(2, listOf("B", "Y")),
    SCISSORS(3, listOf("C", "Z"));
}
private val winningToLosingAction = mapOf(ROCK to SCISSORS, SCISSORS to PAPER, PAPER to ROCK)

private fun actionFromInput(input: String) = Action.values().first { it.representations.contains(input) }
private fun playActions(myAction: Action, opponentAction: Action) = when (opponentAction) {
    myAction -> 3
    winningToLosingAction[myAction] -> 6
    else -> 0
}

fun part01(inputLines: List<String>): Int {
    return inputLines.map { round ->
        val actions = round.split(" ")
        val opponentAction = actionFromInput(actions[0])
        val myAction = actionFromInput(actions[1])

        myAction.score + playActions(myAction, opponentAction)
    }.sum()
}

fun part02(inputLines: List<String>): Int {
    return inputLines.map { round ->
        val actions = round.split(" ")
        val opponentAction = actionFromInput(actions[0])
        val myAction = when (actions[1]) {
            "X" -> winningToLosingAction[opponentAction]!!
            "Y" -> opponentAction
            "Z" -> winningToLosingAction.entries.first { entry -> entry.value == opponentAction }.key
            else -> throw IllegalArgumentException()
        }

        myAction.score + playActions(myAction, opponentAction)
    }.sum()
}

fun main() {
    val inputLines = Paths.get(INPUT_FILE).toFile().readLines()

    println("Result 1: ${part01(inputLines)}")
    println("Result 2: ${part02(inputLines)}")
}

