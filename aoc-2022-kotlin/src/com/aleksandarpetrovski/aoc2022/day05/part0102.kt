package com.aleksandarpetrovski.aoc2022.day05

import com.aleksandarpetrovski.aoc2022.readInputLines
import java.util.Stack

val movementPattern = Regex("move (\\d+) from (\\d+) to (\\d+)")

fun main() {
    val input = readInputLines(day = 5)
    val movements = input.filter { movementPattern.matches(it) }

    val resultPart1 = movePart1(movements, initStacks(input)).getTopElements()
    val resultPart2 = movePart2(movements, initStacks(input)).getTopElements()

    println("Result Part 1: $resultPart1")
    println("Result Part 2: $resultPart2")
}

private fun initStacks(input: List<String>): MutableMap<Int, Stack<Char>> {
    val stacks = mutableMapOf<Int, Stack<Char>>()

    val lineIndexWithStackIdentifiers = input.indexOfFirst { it.startsWith(" 1 ") }

    for (lineIndex in lineIndexWithStackIdentifiers - 1 downTo 0) {
        val stackLine = input[lineIndex]
        for (cratePosition in 1 until stackLine.length step 4) {
            val crateIdentifier = input[lineIndexWithStackIdentifiers][cratePosition].digitToInt()
            if (!stacks.containsKey(crateIdentifier)) {
                stacks[crateIdentifier] = Stack()
            }

            if (stackLine[cratePosition].isUpperCase()) {
                stacks[crateIdentifier]!!.add(stackLine[cratePosition])
            }
        }
    }

    return stacks
}

private fun Map<Int, Stack<Char>>.getTopElements() = (1..this.size).map {
    this[it]!!.pop()
}.joinToString("")

private fun movePart1(movements: List<String>, stacks: MutableMap<Int, Stack<Char>>): MutableMap<Int, Stack<Char>> {
    movements.forEach { line ->
        val (size, from, to) = movementPattern.find(line)!!.destructured
        repeat(size.toInt()) {
            stacks[to.toInt()]!!.add(stacks[from.toInt()]!!.pop())
        }
    }
    return stacks
}

private fun movePart2(movements: List<String>, stacks: MutableMap<Int, Stack<Char>>): MutableMap<Int, Stack<Char>> {
    movements.forEach { line ->
        val (size, from, to) = movementPattern.find(line)!!.destructured
        val localStack = Stack<Char>()
        repeat(size.toInt()) {
            localStack.add(stacks[from.toInt()]!!.pop())
        }
        repeat(size.toInt()) {
            stacks[to.toInt()]!!.add(localStack.pop())
        }

    }
    return stacks
}
