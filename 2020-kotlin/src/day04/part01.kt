package day04

import java.nio.file.Paths
import java.util.*

val validFields = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

fun main() {
    parsePassports().count {
            passport -> validFields.all { field -> passport.contains(field) }
    }.apply {
        println("Valid passports: $this")
    }
}

private fun parsePassports(): LinkedList<Set<String>> {
    val parsedPassports = LinkedList<Set<String>>()

    Paths.get("src/day04/input.in").toFile().readLines().reduce { previousLine, currentLine ->
        if (currentLine.isBlank()) {
            parsedPassports.add(parsePass(previousLine))
            ""
        } else {
            "$previousLine $currentLine"
        }
    }.also { lastLine ->
        parsedPassports.add(parsePass(lastLine))
    }

    return parsedPassports
}

private fun parsePass(input: String): Set<String> {
    return input.split(" ").map { it.split(":")[0] }.toSet()
}