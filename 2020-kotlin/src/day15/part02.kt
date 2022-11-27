package day15

import java.nio.file.Paths

fun main() {
    val startNumbers = Paths.get("src/day15/input.in").toFile().readLines()[0].split(",").map { it.toInt() }.toMutableList()

    val lastIndex = mutableMapOf<Int, Int>()
    val secondLastIndex = mutableMapOf<Int, Int>()

    // save the indexes of the start numbers
    for (i in 0 until startNumbers.size) {
        val number = startNumbers[i]

        if (lastIndex.containsKey(number)) {
            secondLastIndex[number] = lastIndex[number]!!
        }

        lastIndex[number] = i
    }

    var count = startNumbers.size
    var lastNumber = startNumbers.last()

    // calculate the rest of the numbers
    while (count < 30000000) {
        // find next number
        val nextNumber = if (!secondLastIndex.containsKey(lastNumber)) {
            0
        } else {
            lastIndex[lastNumber]!! - secondLastIndex[lastNumber]!!
        }

        // save the new indexes
        if (lastIndex.containsKey(nextNumber)) {
            secondLastIndex[nextNumber] = lastIndex[nextNumber]!!
        }
        lastIndex[nextNumber] = count

        // prepare for the next step
        count++
        lastNumber = nextNumber
    }

    println(lastNumber)
}