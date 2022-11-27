package day14

import java.nio.file.Paths

fun main() {
    val memoryAddressRegex = Regex("\\[(.*)]")
    val inputLines = Paths.get("src/day14/input.in").toFile().readLines()

    val memory = HashMap<Long, Long>()
    var mask = inputLines[0].substring(7)

    for (i in 1 until inputLines.size) {
        val line = inputLines[i]

        if (line.startsWith("mask")) {
            mask = line.substring(7)
        } else {
            val value = line.split(" = ")[1].toLong()
            val address = memoryAddressRegex.find(line)!!.groupValues[1].toLong()
            val binaryAddress = address.toString(2).padStart(36, '0')
            getAddresses(binaryAddress, mask, "").forEach {
                memory[it] = value
            }
        }
    }

    println(memory.values.sum())
}

private fun getAddresses(address: String, mask: String, prefix: String): List<Long> {
    if (prefix.length == 36) {
        return mutableListOf(prefix.toLong(2))
    }

    if (mask[prefix.length] == '0') {
        return getAddresses(address, mask, prefix + address[prefix.length])
    }

    if (mask[prefix.length] == '1') {
        return getAddresses(address, mask, prefix + '1')
    }

    return getAddresses(address, mask, prefix + '0') +
            getAddresses(address, mask, prefix + '1')
}