package day10

import java.nio.file.Paths
import java.util.*

fun main() {
    val analyzedLines = Paths.get("src/day10/input.in").toFile().readLines().map { line ->
        val openingCharsStack = Stack<Char>()

        val firstCorruptedChar = line.firstOrNull { char ->
            if (isOpening(char)) {
                openingCharsStack.push(char)
                false
            } else {
                val lastOpeningChar = openingCharsStack.pop()
                openToCloseMapping[lastOpeningChar] != char
            }
        }

        if (firstCorruptedChar != null) {
            Pair('c', firstCorruptedChar.toString())
        } else {
            Pair('i', openingCharsStack.map { openingChar -> openToCloseMapping[openingChar] }.reversed().joinToString(""))
        }
    }

    val resultPart1 = analyzedLines.filter { it.first == 'c' }
        .map { it.second[0] }
        .sumOf { corruptedPoints[it]!! }

    val resultPart2 = analyzedLines.filter { it.first == 'i' }
        .map { it.second }
        .map { closingChars -> closingChars
                .map { closingChar -> incompletePoints[closingChar]!!.toLong()}
                .reduce { sum, nextChar -> 5 * sum + nextChar }
        }
        .sorted()
        .let { incompleteLines -> incompleteLines[incompleteLines.size / 2] }

    println("RESULT PART 1: $resultPart1")
    println("RESULT PART 2: $resultPart2")
}

val openToCloseMapping = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
val corruptedPoints = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
val incompletePoints = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

fun isOpening(input: Char) = openToCloseMapping.keys.contains(input)