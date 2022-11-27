package day14

import java.lang.StringBuilder
import java.nio.file.Paths

fun main() {
    val memoryAddressRegex = Regex("\\[(.*)]")

    val inputLines = Paths.get("src/day14/input.in").toFile().readLines()

    val memory = HashMap<Int, Long>()
    var mask = inputLines[0].substring(7)

    for (i in 1 until inputLines.size) {
        val line = inputLines[i]

        if (line.startsWith("mask")) {
            mask = line.substring(7)
        } else {
            val address = memoryAddressRegex.find(line)!!.groupValues[1].toInt()
            val valueWithAppliedMask = getValueWithAppliedMask(line, mask)
            memory[address] = valueWithAppliedMask
        }
    }

    println(memory.values.sum())
}

private fun getValueWithAppliedMask(line: String, mask: String): Long {
    val value = line.split(" = ")[1].toInt().toString(2).padStart(36, '0')
    val resultBuilder = StringBuilder()
    for (i in 0..35) {
        if (mask[i] == 'X') {
            resultBuilder.append(value[i])
        } else {
            resultBuilder.append(mask[i])
        }
    }
    return resultBuilder.toString().toLong(2)
}