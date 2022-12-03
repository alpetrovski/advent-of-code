package day08

import java.nio.file.Paths

fun main() {
    val displays = Paths.get("2021-kotlin/src/day08/input.in").toFile().readLines().map { line ->
        val parts = line.split("|")
        Display(parts[0].split(" ").filter { it.isNotBlank() }, parts[1].split(" ").filter { it.isNotBlank() })
    }

    val allowedDigits = arrayOf(1, 4, 7, 8)
    val resultPart1 = displays.flatMap { display -> display.getInputDigits().asIterable() }
        .count { allowedDigits.contains(it) }

    val resultPart2 = displays.sumOf { display ->
        display.getInputDigits().reduce { acc, next -> 10 * acc + next }
    }

    println("RESULT part 01: $resultPart1")
    println("RESULT part 02: $resultPart2")
}

class Display(digitCodes: List<String>, private val inputCodes: List<String>) {
    private var digitMap = arrayOfNulls<Set<Char>>(10)

    init {
        val digitSets = digitCodes.map { it.toCharArray().toSet() }
        digitMap[1] = digitSets.first { it.size == 2 }
        digitMap[4] = digitSets.first { it.size == 4 }
        digitMap[7] = digitSets.first { it.size == 3 }
        digitMap[8] = digitSets.first { it.size == 7 }

        digitMap[6] = digitSets.filter { it.size == 6 }.first { !it.containsAll(digitMap[1]!!) }
        digitMap[9] = digitSets.filter { it.size == 6 }.first { it.containsAll(digitMap[4]!!) }
        digitMap[0] = digitSets.filter { it.size == 6 }.first { it != digitMap[6] && it != digitMap[9] }

        digitMap[5] = digitSets.filter { it.size == 5 }.first { digitMap[6]!!.containsAll(it) }
        digitMap[3] = digitSets.filter { it.size == 5 }.first { it != digitMap[5] && digitMap[9]!!.containsAll(it) }
        digitMap[2] = digitSets.filter { it.size == 5 }.first { it != digitMap[5] && it != digitMap[3] }
    }

    fun getInputDigits() = inputCodes.map { code ->
        digitMap.indexOf(code.toCharArray().toSet())
    }.toIntArray()
}
