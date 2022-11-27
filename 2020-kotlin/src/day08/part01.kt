package day08

import java.nio.file.Paths

data class Command(
    val nextCommandIndex: Int,
    val incrementAccBy: Int = 0,
    var executed: Boolean = false)

fun main() {

    val commands = Paths.get("src/day08/input.in").toFile().readLines().mapIndexed { index, command ->
        val commandParts = command.split(" ")
        when (commandParts[0]) {
            "acc" -> Command(nextCommandIndex = index + 1, incrementAccBy = commandParts[1].toInt())
            "jmp" -> Command(nextCommandIndex = index + commandParts[1].toInt())
            else -> Command(nextCommandIndex = index + 1)
        }
    }

    var accumulator = 0
    var currentCommand = commands[0]
    while (currentCommand.nextCommandIndex < commands.size && !currentCommand.executed) {
        currentCommand.executed = true
        accumulator += currentCommand.incrementAccBy
        currentCommand = commands[currentCommand.nextCommandIndex]
    }

    println(accumulator)
}