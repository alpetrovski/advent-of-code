package day14

import java.nio.file.Paths

val cache = HashMap<String, Map<Char, Long>>()

fun main() {
    val input = Paths.get("src/day14/input.in").toFile().readLines()

    val rules = input.filter { it.contains("->") }.associate {
        val ruleParts = it.split(" -> ")
        Pair(ruleParts[0][0].toString() + ruleParts[0][1].toString(), ruleParts[1][0])
    }

    val resultPart1 = getCharCountsForTemplate(input[0], rules, 10).map { it.value }.let {
        it.maxOrNull()!! - it.minOrNull()!!
    }

    val resultPart2 = getCharCountsForTemplate(input[0], rules, 40).map { it.value }.let {
        it.maxOrNull()!! - it.minOrNull()!!
    }

    println("Result Part 1: $resultPart1")
    println("Result Part 2: $resultPart2")
}

fun getCharCountsForTemplate(input: String, rules: Map<String, Char>, stepsToGo: Int): Map<Char, Long> {
    var result = HashMap<Char, Long>()

    (0 until input.length - 1).forEach { index ->
        getCharCountBetween(input[index], input[index + 1], rules, stepsToGo - 1)
            .forEach { (key, value) -> result[key] = result.getOrDefault(key, 0) + value }
    }

    input.toCharArray().forEach { char -> result[char] = result.getOrDefault(char, 0) + 1 }

    return result
}

fun getCharCountBetween(char1: Char, char2: Char, rules: Map<String, Char>, stepsToGo: Int): Map<Char, Long> {
    val cacheKey = "$char1-$char2-$stepsToGo"
    if (cache.containsKey(cacheKey)) {
        return cache[cacheKey]!!
    }

    val insertedChar = rules[char1.toString() + char2.toString()]!!
    val insertedCharCount = mapOf(insertedChar to 1L)

    if (stepsToGo == 0) {
        return insertedCharCount
    }

    val charsCount1 = getCharCountBetween(char1, insertedChar, rules, stepsToGo - 1)
    val charsCount2 = getCharCountBetween(insertedChar, char2, rules, stepsToGo - 1)

    var resultingMap = (charsCount1.keys + charsCount2.keys + insertedCharCount.keys).associateWith {
        (charsCount1[it] ?: 0) + (charsCount2[it] ?: 0) + (insertedCharCount[it] ?: 0)
    }.toMutableMap()

    cache[cacheKey] = resultingMap
    return resultingMap
}