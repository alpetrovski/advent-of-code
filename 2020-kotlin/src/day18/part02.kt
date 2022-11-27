package day18

import java.nio.file.Paths
import java.util.*

fun main() {
    val result = Paths.get("src/day18/input.in").toFile().readLines().map { exp ->
        evaluateExpressionWithSumPrecedence(exp.replace(" ", "").toCharArray().map { it.toString() }.toList())
    }.sum()
    println(result)
}

fun evaluateExpressionWithSumPrecedence(exp: List<String>): Long {
    var currentExpression = LinkedList<String>()
    val stackedExpressions = Stack<LinkedList<String>>()

    for (item in exp) {
        when (item) {
            "(" -> {
                stackedExpressions.push(currentExpression)
                currentExpression = LinkedList()
            }
            ")" -> {
                val localResult = evaluateSimpleExpressionWithSumPrecedence(currentExpression)
                currentExpression = stackedExpressions.pop()
                currentExpression.add(localResult.toString())
            }
            else -> {
                currentExpression.add(item)
            }
        }
    }

    return evaluateSimpleExpressionWithSumPrecedence(currentExpression)
}

fun evaluateSimpleExpressionWithSumPrecedence(exp: LinkedList<String>): Long {
    while (exp.contains("+")) {
        val indexOfPlus = exp.indexOfFirst { it == "+" }
        val nr1 = exp[indexOfPlus - 1]
        val nr2 = exp[indexOfPlus + 1]
        exp.removeAt(indexOfPlus - 1)
        exp.removeAt(indexOfPlus - 1)
        exp.removeAt(indexOfPlus - 1)
        exp.add(indexOfPlus - 1, (nr1.toLong() + nr2.toLong()).toString())
    }

    while (exp.contains("*")) {
        val indexOfMultiplier = exp.indexOfFirst { it == "*" }
        val nr1 = exp[indexOfMultiplier - 1]
        val nr2 = exp[indexOfMultiplier + 1]
        exp.removeAt(indexOfMultiplier - 1)
        exp.removeAt(indexOfMultiplier - 1)
        exp.removeAt(indexOfMultiplier - 1)
        exp.add(indexOfMultiplier - 1, (nr1.toLong() * nr2.toLong()).toString())
    }

    return exp[0].toLong()
}