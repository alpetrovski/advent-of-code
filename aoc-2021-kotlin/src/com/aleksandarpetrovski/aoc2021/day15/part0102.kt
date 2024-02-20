package com.aleksandarpetrovski.aoc2021.day15

import java.lang.Integer.min
import java.nio.file.Paths

fun main() {
    val cave = Paths.get("2021-kotlin/src/day15/input.in").toFile().readLines().map { line ->
        line.toCharArray().map { it.toString().toInt() }.toIntArray()
    }.toTypedArray()

    val memo = createMemo(cave.size)

    var hasChange = true
    while (hasChange) {
        hasChange = recalculateMemo(cave, memo)
    }

    println("Result Part 1: ${memo.last().last()}")

    val biggerCave = Array(cave.size * 5) { IntArray(cave.size * 5) }
    biggerCave.indices.forEach { x ->
        biggerCave.indices.forEach { y ->
            val nextPartValue = cave[x % cave.size][y % cave.size] + x / cave.size + y / cave.size
            biggerCave[x][y] = if (nextPartValue > 9) nextPartValue - 9 else nextPartValue
        }
    }

    val biggerCaveMemo = createMemo(biggerCave.size)

    hasChange = true
    while (hasChange) {
        hasChange = recalculateMemo(biggerCave, biggerCaveMemo)
    }

    println("Result Part 2: ${biggerCaveMemo.last().last()}")
}

private fun recalculateMemo(cave: Array<IntArray>, memo: Array<IntArray>): Boolean {
    var hasChange = false
    cave.indices.forEach { x ->
        cave.indices.forEach { y ->
            if (x == 0 && y == 0) {
                memo[x][y] = 0
            } else {
                val newResult = getMinSibling(memo, x, y) + cave[x][y]
                if (newResult != memo[x][y]) {
                    memo[x][y] = newResult
                    hasChange = true
                }
            }
        }
    }
    return hasChange
}

private fun createMemo(caveSize: Int): Array<IntArray> {
    val memo = Array(caveSize) { IntArray(caveSize) }
    memo.indices.forEach { x ->
        memo.indices.forEach { y ->
            memo[x][y] = Integer.MAX_VALUE
        }
    }
    return memo
}

private fun getMinSibling(cave: Array<IntArray>, x: Int, y: Int): Int {
    var result = Integer.MAX_VALUE
    if (x > 0) {
        result = min(result, cave[x - 1][y])
    }
    if (y > 0) {
        result = min(result, cave[x][y - 1])
    }
    if (x < cave.size - 1) {
        result = min(result, cave[x + 1][y])
    }
    if (y < cave.size - 1) {
        result = min(result, cave[x][y + 1])
    }
    return result
}
