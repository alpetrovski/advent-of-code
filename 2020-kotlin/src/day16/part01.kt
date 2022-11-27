package day16

import java.nio.file.Paths

fun main() {
    val ruleRegex = Regex("^.*\\: ([0-9]*)\\-([0-9]*) or ([0-9]*)-([0-9]*)")
    val lines = Paths.get("src/day16/input.in").toFile().readLines()

    val rules = lines.mapNotNull { ruleRegex.matchEntire(it)?.groupValues }
        .map {
            listOf(IntRange(it[1].toInt(), it[2].toInt()), IntRange(it[3].toInt(), it[4].toInt()))
        }.flatten()

    val nearbyTicketsIndexStart = lines.indexOf("nearby tickets:") + 1

    val result = lines.subList(nearbyTicketsIndexStart, lines.size).map { line ->
        line.split(",").map{it.toInt()}.filter { value ->
            !rules.any { range -> range.contains(value) }
        }.sum()
    }.sum()

    println(result)
}