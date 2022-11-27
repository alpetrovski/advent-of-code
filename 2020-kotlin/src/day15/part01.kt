package day15

import java.nio.file.Paths

fun main() {
    val numbers = Paths.get("src/day15/input.in").toFile().readLines()[0].split(",").map { it.toInt() }.toMutableList()
    while (numbers.size < 2020) {
        val lastNumber = numbers.last()
        if (numbers.count { it == lastNumber } == 1) {
            numbers.add(0)
        } else {
            val lastIndex = numbers.size - 1
            val secondLastIndex = numbers.subList(0, numbers.size - 1).lastIndexOf(lastNumber)
            numbers.add(lastIndex - secondLastIndex)
        }
    }
    println(numbers[2019])
}