package day13

import java.nio.file.Paths

fun main() {
    val inputLines = Paths.get("src/day13/input.in").toFile().readLines()

    val arrivalTime = inputLines[0].toInt()
    val busIntervals = inputLines[1].split(",").filter { "x" != it }.map { it.toInt() }

    val departureTime = busIntervals.minOf { (arrivalTime / it + 1) * it }

    val busId = busIntervals.first { departureTime % it == 0 }
    println(busId * (departureTime - arrivalTime))
}