package day08

import java.nio.file.Paths

fun main() {
    val displays = Paths.get("src/day08/input.in").toFile().readLines().map { line ->
        val parts = line.split("|")
        Display(parts[0].split(" ").filter { it.isNotBlank() }, parts[1].split(" ").filter { it.isNotBlank() })
    }

    val resultPart1 = displays.sumOf { display ->
        display.getInputDigits().count { digit ->
            arrayOf(1, 4, 7, 8).contains(digit)
        }
    }

    val resultPart2 = displays.sumOf { display ->
        display.getInputDigits().reduce { acc, i -> 10 * acc + i }
    }

    println("RESULT part 01: $resultPart1")
    println("RESULT part 02: $resultPart2")
}

class Display(digitCodes: List<String>, private val inputCodes: List<String>) {
    private var digitMap = arrayOfNulls<Set<Char>>(10)

    init {
        digitMap[1] = digitCodes.toSets().first { it.size == 2 }
        digitMap[4] = digitCodes.toSets().first { it.size == 4 }
        digitMap[7] = digitCodes.toSets().first { it.size == 3 }
        digitMap[8] = digitCodes.toSets().first { it.size == 7 }

        digitMap[6] = digitCodes.toSets().filter { it.size == 6 }
            .first { !it.containsAll(digitMap[1]!!) }
        digitMap[9] = digitCodes.toSets().filter { it.size == 6 }
            .first { it.containsAll(digitMap[4]!!) }
        digitMap[0] = digitCodes.toSets().filter { it.size == 6 }
            .first { !digitMap[6]!!.containsAll(it) && !digitMap[9]!!.containsAll(it) }

        digitMap[5] = digitCodes.toSets().filter { it.size == 5 }
            .first { digitMap[6]!!.containsAll(it) }
        digitMap[3] = digitCodes.toSets().filter { it.size == 5 }
            .first { digitMap[9]!!.containsAll(it) && !digitMap[5]!!.containsAll(it) }
        digitMap[2] = digitCodes.toSets().filter { it.size == 5 }
            .first { !digitMap[3]!!.containsAll(it) && !digitMap[5]!!.containsAll(it) }

    }

    fun getInputDigits() = inputCodes.map {
        digitMap.indexOf(it.toSet())
    }.toIntArray()

    private fun List<String>.toSets() = this.map { it.toSet() }
    private fun String.toSet() = this.toCharArray().toSet()
}