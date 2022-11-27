package day11

import java.nio.file.Paths

fun main() {
    var result = Paths.get("src/day11/input.in").toFile().readLines()
        .map { it.toCharArray() }.toTypedArray()
    var newResult = transform(result)
    while (!matricesEqual(result, newResult)) {
        result = newResult
        newResult = transform(result)
    }
    val allOccupied = result.map { row ->
        row.filter { c -> c == '#' }.count()
    }.sum()

    println(allOccupied)
}

private fun transform(input: Array<CharArray>): Array<CharArray> {
    var result = Array(input.size) { _ -> CharArray(input[0].size) }
    for (i in input.indices) {
        result[i] = CharArray(input[i].size)
        for (j in input[i].indices) {
            if (input[i][j] == 'L' && countAdjacentOccupied(input, i, j) == 0) {
                result[i]!![j] = '#'
            } else if (input[i][j] == '#' && countAdjacentOccupied(input, i, j) >= 4) {
                result[i]!![j] = 'L'
            } else {
                result[i]!![j] = input[i][j]
            }
        }
    }
    return result
}

private fun countAdjacentOccupied(input: Array<CharArray>, i: Int, j: Int): Int {
    var result = 0
    for (l in i-1..i+1) {
        for (k in j-1..j+1) {
            if (l>=0 && l < input.size && k >= 0 && k < input[i].size && (i != l || j != k)) {
                if (input[l][k] == '#') {
                    result++
                }
            }
        }
    }

    return result
}

private fun matricesEqual(input1: Array<CharArray>, input2: Array<CharArray>): Boolean {
    for (i in input1.indices) {
        for (j in input1[i].indices) {
            if (input1[i][j] != input2[i][j]) {
                return false
            }
        }
    }
    return true
}
