package day04

import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

val hairColorRegex = Regex("^#[a-f0-9]{6}$")
val heightCmRegex = Regex("^[0-9]{3}cm$")
val heightInRegex = Regex("^[0-9]{2}in$")

fun main() {
    parsePassports().count {
        validatePassport(it)
    }.apply {
        println("Valid passports: $this")
    }
}

private fun parsePassports(): LinkedList<Map<String, String>> {
    val parsedPassports = LinkedList<Map<String, String>>()

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

private fun parsePass(input: String): Map<String, String> {
    val result = HashMap<String, String>()
    input.trim().split(" ").forEach {
        result[it.split(":")[0]] = it.split(":")[1]
    }
    return result
}

private fun validatePassport(passportFields: Map<String, String>): Boolean {
    var valid = true
    try {
        valid = valid && passportFields.getOrDefault("byr", "").let {
            it.length == 4 && it.toInt() in 1920..2002
        }

        valid = valid && passportFields.getOrDefault("iyr", "").let {
            it.length == 4 && it.toInt() in 2010..2020
        }

        valid = valid && passportFields.getOrDefault("eyr", "").let {
            it.length == 4 && it.toInt() in 2020..2030
        }

        valid = valid && passportFields.getOrDefault("hgt", "").let {
            (it.endsWith("cm") && heightCmRegex.matches(it) && it.replace("cm", "").toInt() in 150..193)
                    || (it.endsWith("in") && heightInRegex.matches(it) && it.replace("in", "").toInt() in 59..76)
        }

        valid = valid && passportFields.getOrDefault("hcl", "").let {
            hairColorRegex.matches(it)
        }

        valid = valid && passportFields.getOrDefault("ecl", "").let {
            listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it)
        }

        valid = valid && passportFields.getOrDefault("pid", "").let {
            it.length == 9 && it.toLong() in 0 .. 999999999
        }

    } catch (ex: Exception) {
        valid = false
    }

    return valid
}