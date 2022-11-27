package day08

import java.nio.file.Paths

data class CommandWithParam(
    var command: String,
    val param: Int,
    val index: Int
)
fun main() {
    val commandsWithParams = Paths.get("src/day08/input.in").toFile().readLines().mapIndexed { index, line ->
        val commandParts = line.split(" ")
        CommandWithParam(index = index, command = commandParts[0], param = commandParts[1].toInt())
    }

    for (command in commandsWithParams) {
        val result = tryWithSwitchedCommand(commandsWithParams, command)
        if (result != null) {
            println(result)
            return
        }
    }
}

private fun tryWithSwitchedCommand(commandsWithParams: List<CommandWithParam>, command: CommandWithParam): Int? {
    val changedList = commandsWithParams.map { it.copy() }
    if (command.command == "nop") {
        changedList[command.index].command = "jmp"
    } else if (command.command == "jmp") {
        changedList[command.index].command = "nop"
    } else {
        return null
    }
    return tryFullyExecuteAndCalculate(changedList)
}

private fun tryFullyExecuteAndCalculate(commands: List<CommandWithParam>): Int? {
    val executedCommands = HashSet<Int>()

    var accumulator = 0

    var nextCommandIndex = 0
    while (nextCommandIndex != commands.size) {
        val nextCommand = commands[nextCommandIndex]
        if (executedCommands.contains(nextCommandIndex)) {
            return null
        }

        executedCommands.add(nextCommandIndex)

        when (nextCommand.command) {
            "nop" -> {
                nextCommandIndex++
            }
            "acc" -> {
                accumulator += nextCommand.param
                nextCommandIndex++
            }
            "jmp" -> {
                nextCommandIndex += nextCommand.param
            }
        }
    }
    return accumulator
}

