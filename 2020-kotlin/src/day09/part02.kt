package day09

import java.nio.file.Paths
import java.util.*

fun main() {
    println(findWeakness("src/day09/input.in"))
}

private fun findWeakness(filePath: String): Long? {
    val numbers = Paths.get(filePath).toFile().readLines().map { it.toLong() }
    val invalidNumber = findInvalidNumber(numbers)!!
    val contiguousRange = LinkedList<Long>()

    numbers.forEach {number ->
        contiguousRange.addLast(number)

        while (contiguousRange.sum() > invalidNumber) {
            contiguousRange.removeFirst()
        }

        if (contiguousRange.sum() == invalidNumber) {
            return contiguousRange.minOrNull()!! + contiguousRange.maxOrNull()!!
        }
    }
    return null
}
