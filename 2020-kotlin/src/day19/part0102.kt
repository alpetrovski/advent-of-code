package day19

import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap

val cache = ConcurrentHashMap<String, Boolean>()
lateinit var rules: Map<String, String>

fun main() {
    val ruleRegex = Regex("[0-9]*:.*")
    val messageRegex = Regex("[ab]+")

    val inputLines = Paths.get("src/day19/input.in").toFile().readLines()

    rules = inputLines.filter { ruleRegex.matches(it) }
            .map {
                val splittedValues = it.split(":")
                splittedValues[0] to splittedValues[1].trim()
            }.toMap()

    val result = inputLines.filter { messageRegex.matches(it) }
            .filter { message -> satisfiesRule(rules["0"]!!, message) }
            .count()

    println("Result: $result")
}

fun satisfiesRule(ruleExpr: String, message: String): Boolean {
    return cache.getOrPut("$ruleExpr:$message") {
        satisfiesRuleInternal(ruleExpr, message)
    }
}

fun satisfiesRuleInternal(ruleExpr: String, message: String): Boolean {
    // single letter rule
    if (ruleExpr == "\"a\"") {
        return message == "a"
    }

    if (ruleExpr == "\"b\"") {
        return message == "b"
    }

    // OR rule
    if (ruleExpr.contains("|")) {
        val ruleSegments = ruleExpr.split("|")
        return satisfiesRule(ruleSegments[0].trim(), message)
                || satisfiesRule(ruleSegments[1].trim(), message)
    }

    // with sub rules
    val subRules = ruleExpr.split(" ")
    return when (subRules.size) {
        1 -> satisfiesRule(rules[subRules[0]]!!, message)
        2 -> IntRange(1, message.length - 1).any {
            satisfiesRule(rules[subRules[0]]!!, message.substring(0, it))
                    && satisfiesRule(rules[subRules[1]]!!, message.substring(it))
        }
        else -> IntRange(1, message.length - 2).any { i ->
            IntRange(i, message.length - 1).any { j ->
                satisfiesRule(rules[subRules[0]]!!, message.substring(0, i))
                        && satisfiesRule(rules[subRules[1]]!!, message.substring(i, j))
                        && satisfiesRule(rules[subRules[2]]!!, message.substring(j))
            }
        }
    }
}