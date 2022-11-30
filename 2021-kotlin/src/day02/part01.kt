package day02

import java.nio.file.Paths

fun main() {
    val aggregatedCommands = Paths.get("2021-kotlin/src/day02/input.in").toFile().readLines().map {
        val lineComponents = it.split(" ")
        Pair(lineComponents[0], lineComponents[1].toLong())
    }.groupBy { it.first }
        .map { entry -> entry.key to entry.value.sumOf { it.second } }
        .toMap()

    val result = (aggregatedCommands["down"]!! - aggregatedCommands["up"]!!) *
            aggregatedCommands["forward"]!!

    println("RESULT: $result")
}
