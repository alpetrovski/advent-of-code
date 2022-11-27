package day03

import java.nio.file.Paths

fun main() {
    val input = Paths.get("src/day03/input.in").toFile().readLines()
    val result = Integer.parseInt(getOxygen(input), 2) * Integer.parseInt(getCO2(input), 2)

    println("RESULT: $result")
}

fun getOxygen(input: List<String>): String {
    var filtered = input
    for (i in 0 until input[0].length) {
        if (filtered.size == 1) {
            return filtered[0]
        }

        filtered = getMostCommon(i, filtered)
    }
    return filtered[0]
}

fun getCO2(input: List<String>): String {
    var filtered = input
    for (i in 0 until input[0].length) {
        if (filtered.size == 1) {
            return filtered[0]
        }

        filtered = getLeastCommon(i, filtered)
    }
    return filtered[0]
}

fun getMostCommon(index: Int, input: List<String>): List<String> {
    return if (input.count { it[index] == '1' } >= input.count { it[index] == '0' }) {
        input.filter { it[index] == '1' }
    } else {
        input.filter { it[index] == '0' }
    }
}

fun getLeastCommon(index: Int, input: List<String>): List<String> {
    return if (input.count { it[index] == '0' } <= input.count { it[index] == '1' }) {
        input.filter { it[index] == '0' }
    } else {
        input.filter { it[index] == '1' }
    }
}