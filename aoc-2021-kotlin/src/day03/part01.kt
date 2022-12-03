package day03

import java.nio.file.Paths

fun main() {
    val input = Paths.get("2021-kotlin/src/day03/input.in").toFile().readLines()
    val ones = arrayOfNulls<Int>(input[0].length)

    input.forEach {
        (ones.indices).forEach { index ->
            if (it[index] == '1') {
                ones[index] = (ones[index] ?: 0) + 1
            }
        }
    }

    val gamma = Integer.parseInt(ones.map {
        if ((it ?: 0) > (input.size / 2)) {
            '1'
        } else {
            '0'
        }
    }.joinToString(""), 2)

    val epsilon = Integer.parseInt(ones.map {
        if ((it ?: 0) > (input.size / 2)) {
            '0'
        } else {
            '1'
        }
    }.joinToString(""), 2)

    println("RESULT: ${gamma * epsilon}")
}
