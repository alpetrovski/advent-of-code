package day18

import java.nio.file.Paths
import kotlin.math.ceil
import kotlin.math.floor

val firstNumber = Regex("^[\\[\\]\\,]+(\\d+)[\\[\\]\\,]+.*\$")
val lastNumber = Regex("^.*[\\[\\]\\,]+(\\d+)[\\[\\]\\,]+\$")
val contains = Regex("^.*[\\[\\]\\,]+(\\d+)[\\[\\]\\,]+\$")
val numberPair = Regex("^.*(\\[\\d+\\,\\d+\\]).*$")

fun main() {
    val numbers = Paths.get("2021-kotlin/src/day18/input.in").toFile().readLines()

    val resultPart1 = calculateMagnitude(numbers.reduce { number1, number2 -> addNumbers(number1, number2) })
    println("Result Part 1: $resultPart1")

    val resultPart2 = numbers.maxOf { number1 ->
        numbers.maxOf { number2 -> calculateMagnitude(addNumbers(number1, number2)) }
    }
    println("Result Part 2: $resultPart2")
}

fun addNumbers(number1: String, number2: String): String {
    var result = "[$number1,$number2]"
    while (nextExplodable(result) != null || nextSplitable(result) != null) {
        if (nextExplodable(result) != null) {
            result = explode(result)
        } else if (nextSplitable(result) != null) {
            result = split(result)
        }
    }
    return result
}

fun explode(pairs: String): String {
    val startIndex = nextExplodable(pairs)!!
    val endIndex = startIndex + pairs.substring(startIndex).indexOfFirst { it == ']' } + 1

    val digits = pairs.substring(startIndex, endIndex).replace("[", "").replace("]", "").split(",")

    val leftPart = pairs.substring(0, startIndex)
    val rightPart = pairs.substring(endIndex)

    val updatedLeftPart = if (lastNumber.matches(leftPart)) {
        val newValue = lastNumber.replace(leftPart) { (it.groups[1]!!.value.toInt() + digits[0].toInt()).toString() }
        val match = lastNumber.find(leftPart)!!.groups[1]!!
        leftPart.replaceBetween(match.range.first, match.range.last + 1, newValue)
    } else {
        leftPart
    }

    val updatedRightPart = if (firstNumber.matches(rightPart)) {
        val newValue = firstNumber.replace(rightPart) { (it.groups[1]!!.value.toInt() + digits[1].toInt()).toString() }
        val match = firstNumber.find(rightPart)!!.groups[1]!!
        rightPart.replaceBetween(match.range.first, match.range.last + 1, newValue)
    } else {
        rightPart
    }

    return updatedLeftPart + "0" + updatedRightPart
}

fun split(pairs: String): String {
    val startIndex = nextSplitable(pairs)!!
    val endIndex = startIndex + pairs.substring(startIndex).indexOfFirst { it == ']' || it == ',' }
    val number = pairs.substring(startIndex, endIndex).toInt()
    val splitNumber = "[${floor(number / 2.0).toInt()},${ceil(number / 2.0).toInt()}]"
    return pairs.replaceBetween(startIndex, endIndex, splitNumber)
}

fun String.replaceBetween(startIndex: Int, endIndex: Int, value: String): String =
    this.substring(0, startIndex) + value + this.substring(endIndex)

fun nextExplodable(pairs: String): Int? {
    var counter = 0
    return pairs.indices.firstOrNull {
        when (pairs[it]) {
            '[' -> counter++
            ']' -> counter--
        }
        counter >= 5
    }
}

fun nextSplitable(pairs: String): Int? = pairs.indices.firstOrNull {
    it < pairs.length - 1 && pairs[it].isDigit() && pairs[it + 1].isDigit()
}

fun calculateMagnitude(number: String): Int {
    var reducedNumber = number
    while (numberPair.matches(reducedNumber)) {
        val matchedPair = numberPair.find(reducedNumber)!!.groups[1]!!
        reducedNumber = reducedNumber.substring(0, matchedPair.range.first) +
                calculatePairMagnitude(matchedPair.value).toString() +
                reducedNumber.substring(matchedPair.range.last + 1)
    }
    return reducedNumber.toInt()
}

fun calculatePairMagnitude(pair: String) = pair
    .removePrefix("[")
    .removeSuffix("]")
    .split(",")
    .let { numbers -> numbers[0].toInt() * 3 + numbers[1].toInt() * 2 }
