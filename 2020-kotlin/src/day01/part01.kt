package day01

import java.nio.file.Paths

fun main() {
    val numbers = Paths.get("src/day01/input.in").toFile().readLines().map { it.toInt() }

    for (i in 0 until numbers.size - 1) {
        for (j in i + 1 until numbers.size) {
            if (2020 == numbers[i] + numbers[j]) {
                println(numbers[i] * numbers[j])
                return
            }
        }
    }
}