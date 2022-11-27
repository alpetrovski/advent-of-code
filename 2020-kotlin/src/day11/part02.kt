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
            } else if (input[i][j] == '#' && countAdjacentOccupied(input, i, j) >= 5) {
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
    var ti: Int
    var tj: Int

    // up
    ti = i
    tj = j
    for (t in i - 1 downTo 0) {
        ti--
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
        }
    }

    // up - right
    ti = i
    tj = j
    for (t in i - 1 downTo 0) {
        ti--
        tj++
        if (tj == input.size) {
            break
        }
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
        }
    }

    // right
    ti = i
    tj = j
    for (t in j + 1 until input.size) {
        tj++
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
        }
    }

    // right-down
    ti = i
    tj = j
    for (t in j + 1 until input.size) {
        tj++
        ti++
        if (ti == input.size) {
            break
        }
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
        }
    }

    // down
    ti = i
    tj = j
    for (t in i + 1 until input.size) {
        ti++
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
        }
    }

    // down-left
    ti = i
    tj = j
    for (t in j-1 downTo 0) {
        ti++
        tj--
        if (ti == input.size) {
            break
        }
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
        }
    }

    // left
    ti = i
    tj = j
    for (t in j - 1 downTo 0) {
        tj--
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
        }
    }

    // left - up
    ti = i
    tj = j
    for (t in j - 1 downTo 0) {
        tj--
        ti--
        if (ti < 0) {
            break
        }
        if (input[ti][tj] == '#') {
            result++
            break
        } else if (input[ti][tj] == 'L') {
            break
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
