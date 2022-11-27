package day12

import java.nio.file.Paths
import kotlin.math.abs

val directions = listOf('E', 'S', 'W', 'N')

fun main() {
    var direction = 'E'
    val sums = mutableMapOf('E' to 0, 'S' to 0, 'W' to 0, 'N' to 0)

    Paths.get("src/day12/input.in").toFile().readLines().forEach { instruction ->
        val command = instruction[0]
        when {
            directions.contains(command) -> sums[command] = sums[command]!! + instruction.value()
            command == 'F' -> sums[direction] = sums[direction]!! + instruction.value()
            command == 'R' -> direction = switchDirection(direction, instruction.value() / 90)
            command == 'L' -> direction = switchDirection(direction, 4 - (instruction.value() / 90) % 4)
        }
    }

    println(abs(sums.getOrDefault('E', 0) - sums.getOrDefault('W', 0))
            + abs(sums.getOrDefault('N', 0) - sums.getOrDefault('S', 0)))
}

private fun String.value(): Int {
    return this.substring(1).toInt()
}

private fun switchDirection(currentDirection: Char, times: Int): Char {
    val index = (directions.indexOf(currentDirection) + times) % 4
    return directions[index]
}