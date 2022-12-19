package com.aleksandarpetrovski.aoc2022.day11

import com.aleksandarpetrovski.aoc2022.readInputLines

data class MonkeyPart1(
    val items: MutableList<Long> = mutableListOf(),
    val operation: (Long) -> Long,
    val testFunction: (Long) -> Boolean,
    val trueMonkeyIndex: Int,
    val falseMonkeyIndex: Int,
    var examinedItems: Long = 0
)

data class MonkeyPart2(
    val items: MutableList<Item> = mutableListOf(),
    val operation: (Item) -> Unit,
    val testFunction: (Item) -> Boolean,
    val trueMonkeyIndex: Int,
    val falseMonkeyIndex: Int,
    var examinedItems: Long = 0
)

data class Item(val modulos: MutableMap<Int, Int>) {
    constructor(number: Int, dividers: List<Int>) : this(dividers.associateWith { number % it }.toMutableMap())

    fun isDivisibleWith(divisor: Int) = modulos[divisor] == 0
    fun newMultiplier(newMultiplier: Int) = modulos.keys.forEach { modulos[it] = (modulos[it]!! * newMultiplier) % it }
    fun multiplyWithOld() = modulos.keys.forEach { modulos[it] = (modulos[it]!! * modulos[it]!!) % it }
    fun addValue(value: Int) = modulos.keys.forEach { modulos[it] = (modulos[it]!! + value) % it }
}

fun main() {
    val inputLines = readInputLines(day = 11, isTest = false)
    val dividers = inputLines.filter { it.startsWith("  Test: divisible by ") }
        .map { it.removePrefix("  Test: divisible by ").toInt() }

    val resultPart1 = monkeyBusinessPart1(monkeys = createMonkeysPart1(inputLines))
    println("Result Part 01: $resultPart1")

    val resultPart2 = monkeyBusinessPart2(monkeys = createMonkeysPart2(inputLines, dividers))
    println("Result Part 02: $resultPart2")
}

fun monkeyBusinessPart1(monkeys: List<MonkeyPart1>): Long {
    repeat(20) {
        monkeys.forEach { monkey ->
            monkey.items.forEach { item ->
                val newWorryLevel = monkey.operation.invoke(item)
                if (monkey.testFunction.invoke(newWorryLevel)) {
                    monkeys[monkey.trueMonkeyIndex].items.add(newWorryLevel)
                } else {
                    monkeys[monkey.falseMonkeyIndex].items.add(newWorryLevel)
                }
                monkey.examinedItems++
            }
            monkey.items.clear()
        }
    }

    return monkeys.sortedByDescending { it.examinedItems }.subList(0, 2).map { it.examinedItems }
        .reduce { firstMonkey, secondMonkey -> firstMonkey * secondMonkey }
}

fun monkeyBusinessPart2(monkeys: List<MonkeyPart2>): Long {
    repeat(10000) {
        monkeys.forEach { monkey ->
            monkey.items.forEach { item ->
                monkey.operation.invoke(item)

                if (monkey.testFunction.invoke(item)) {
                    monkeys[monkey.trueMonkeyIndex].items.add(item)
                } else {
                    monkeys[monkey.falseMonkeyIndex].items.add(item)
                }
                monkey.examinedItems++
            }
            monkey.items.clear()
        }
    }
    return monkeys.sortedByDescending { it.examinedItems }.subList(0, 2).map { it.examinedItems }
        .reduce { firstMonkey, secondMonkey -> firstMonkey * secondMonkey }
}

fun createMonkeysPart1(inputLines: List<String>) = inputLines.chunked(7).map { monkeyDescription ->
    val startingItems = monkeyDescription[1].removePrefix("  Starting items: ").split(", ").map { it.toLong() }.toMutableList()

    val operation = if (monkeyDescription[2][23] == '*') {
        if (monkeyDescription[2].substring(25) == "old") {
            { old: Long -> old * old / 3}
        } else {
            { old: Long -> old * monkeyDescription[2].substring(25).toInt() / 3}
        }
    } else {
        { old: Long -> (old + monkeyDescription[2].substring(25).toInt()) / 3}
    }
    val testFunction = { input: Long -> input % monkeyDescription.getDivisibleBy() == 0L }

    MonkeyPart1(
        items = startingItems,
        operation = operation,
        testFunction = testFunction,
        trueMonkeyIndex = monkeyDescription.getTrueMonkeyIndex(),
        falseMonkeyIndex = monkeyDescription.getFalseMonkeyIndex()
    )
}

fun createMonkeysPart2(inputLines: List<String>, dividers: List<Int>) = inputLines.chunked(7).map { monkeyDescription ->
    val startingItems = monkeyDescription.getStartingItems().map { Item(it, dividers) }.toMutableList()

    val operation = if (monkeyDescription[2][23] == '*') {
        if (monkeyDescription[2].substring(25) == "old") {
            { old: Item -> old.multiplyWithOld() }
        } else {
            { old: Item -> old.newMultiplier(monkeyDescription[2].substring(25).toInt()) }
        }
    } else {
        { old: Item -> old.addValue(monkeyDescription[2].substring(25).toInt()) }
    }
    val testFunction = { item: Item -> item.isDivisibleWith(monkeyDescription.getDivisibleBy()) }
    MonkeyPart2(
        items = startingItems,
        operation = operation,
        testFunction = testFunction,
        trueMonkeyIndex = monkeyDescription.getTrueMonkeyIndex(),
        falseMonkeyIndex = monkeyDescription.getFalseMonkeyIndex()
    )
}

fun List<String>.getStartingItems() = this[1].removePrefix("  Starting items: ").split(", ").map { it.toInt() }
fun List<String>.getDivisibleBy() = this[3].removePrefix("  Test: divisible by ").toInt()
fun List<String>.getTrueMonkeyIndex() = this[4].removePrefix("    If true: throw to monkey ").toInt()
fun List<String>.getFalseMonkeyIndex() = this[5].removePrefix("    If false: throw to monkey ").toInt()