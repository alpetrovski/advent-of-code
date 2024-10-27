package com.aleksandarpetrovski.aoc2023.day02

import com.aleksandarpetrovski.aoc2023.readInputLines

data class Game(
    val id: Int,
    val sets: List<Map<String, Int>>
)

val cubeColors = listOf("red", "green", "blue")

fun main() {
    val games = readInputLines(day = 2, isTest = false).map { parseGame(it) }

    println("Result part 1: ${part01(games)}")
    println("Result part 2: ${part02(games)}")
}

fun part01(games: List<Game>): Int {
    val possibilityCriteria = mapOf("red" to 12, "green" to 13, "blue" to 14)
    return games.filter { game ->
        game.sets.all { set ->
            cubeColors.all { color -> (set[color] ?: 0) <= possibilityCriteria[color]!! }
        }
    }.sumOf { game -> game.id }
}

fun part02(games: List<Game>) = games.sumOf { game ->
    val maxCubesPerColor = cubeColors.map { color -> game.sets.maxOf { it[color] ?: 0 } }
    maxCubesPerColor.reduce(Int::times)
}


private fun parseGame(line: String): Game {
    val gameId = line.split(":")[0].removePrefix("Game ").toInt()
    val sets = line.split(":")[1].split(";").map { set ->
        set.split(",").map { it.trim() }.associate { draw ->
            draw.split(" ")[1] to draw.split(" ")[0].toInt()
        }
    }
    return Game(id = gameId, sets = sets)
}