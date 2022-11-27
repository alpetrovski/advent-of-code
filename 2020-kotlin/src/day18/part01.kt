package day18

import java.nio.file.Paths
import java.util.*

fun main() {
    val result = Paths.get("src/day18/input.in").toFile().readLines().map { exp ->
        evaluateExpression(exp.replace(" ", "").toCharArray().map { it.toString() }.toList())
    }.sum()
    println(result)
}

fun evaluateExpression(exp: List<String>): Long {
    var currentExpression: MutableList<String> = LinkedList()
    val stackedExpressions = Stack<MutableList<String>>()

    for (item in exp) {
        when (item) {
            "(" -> {
                stackedExpressions.push(currentExpression)
                currentExpression = LinkedList()
            }
            ")" -> {
                val localResult = evaluateSimpleExpression(currentExpression)
                currentExpression = stackedExpressions.pop()
                currentExpression.add(localResult.toString())
            }
            else -> {
                currentExpression.add(item)
            }
        }
    }

    return evaluateSimpleExpression(currentExpression)
}

fun evaluateSimpleExpression(exp: List<String>): Long {
    if (exp.isEmpty()) {
        return 0L
    }

    var result = exp[0].toLong()
    for (i in 2 until exp.size step 2) {
        if (exp[i-1] == "+") {
            result += exp[i].toLong()
        } else if (exp[i-1] == "*") {
            result *= exp[i].toLong()
        }
    }
    return result
}