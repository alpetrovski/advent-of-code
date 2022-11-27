package day16

import java.nio.file.Paths

fun main() {
    val lines = Paths.get("src/day16/input.in").toFile().readLines()

    // find all the rules
    val ruleRegex = Regex("^(.*)\\: ([0-9]*)\\-([0-9]*) or ([0-9]*)-([0-9]*)")
    val rules = mutableMapOf<String, List<IntRange>>()
    lines.mapNotNull { ruleRegex.matchEntire(it)?.groupValues }.forEach {
        rules[it[1]] = listOf(IntRange(it[2].toInt(), it[3].toInt()), IntRange(it[4].toInt(), it[5].toInt()))
    }

    // find nearby tickets
    val nearbyTicketsIndexStart = lines.indexOf("nearby tickets:") + 1
    val nearbyValidTickets = lines.subList(nearbyTicketsIndexStart, lines.size)
        .map { line -> line.split(",").map{ value -> value.toInt()} }
        .filter { ticket -> ticket.all { value ->
                rules.values.flatten().any { range -> range.contains(value) }
            }
        }

    // your ticket
    val yourTicketIndex = lines.indexOf("your ticket:") + 1
    val yourTicket = lines[yourTicketIndex].split(",").map { it.toInt() }

    // make a map of applicable fields per rule
    val applicableFieldsForRule = mutableMapOf<String, MutableList<Boolean>>()
    rules.entries.forEach { rule ->
        val ruleName = rule.key
        val range1 = rule.value[0]
        val range2 = rule.value[1]

        val applicableFields = nearbyValidTickets[0].map { true }.toMutableList()

        nearbyValidTickets.forEach {ticket ->
            for (i in ticket.indices) {
                if (!range1.contains(ticket[i]) && !range2.contains(ticket[i])) {
                    applicableFields[i] = false
                }
            }
        }
        applicableFieldsForRule[ruleName] = applicableFields
    }

    // find positions of the rules
    val rulePositions = Array(applicableFieldsForRule.size) { _ -> "" }
    while (applicableFieldsForRule.isNotEmpty()) {
        val keyWithOneApplicable = applicableFieldsForRule.entries.filter { it.value.count { it } == 1 }.map { it.key }.first()
        val position = applicableFieldsForRule[keyWithOneApplicable]!!.indexOfFirst { it }
        applicableFieldsForRule.forEach{
            it.value[position] = false
        }
        applicableFieldsForRule.remove(keyWithOneApplicable)
        rulePositions[position] = keyWithOneApplicable
    }

    // find the departure values of your ticket
    var result = 1L
    rulePositions.forEachIndexed { index, value ->
        if (value.startsWith("departure")) {
            result *= yourTicket[index]
        }
    }

    println(result)
}