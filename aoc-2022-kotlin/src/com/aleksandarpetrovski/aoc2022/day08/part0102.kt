package com.aleksandarpetrovski.aoc2022.day08

import com.aleksandarpetrovski.aoc2022.readInputLines

fun main() {
    val treeMap = readInputLines(day = 8, isTest = false).map {
        it.toCharArray().map { tree -> tree.digitToInt() }.toTypedArray()
    }.toTypedArray()

    println("Result Part 1: ${part01(treeMap)}")
    println("Result Part 2: ${part02(treeMap)}")
}

private fun part01(treeMap: Array<Array<Int>>): Int {
    val result = mutableSetOf<Pair<Int, Int>>()

    for (i in treeMap.indices) {
        for (j in treeMap[i].indices) {
            if (isEdgeTree(treeMap, i, j)
                || allOnTheLeftAreSmaller(treeMap, i, j)
                || allOnTheRightAreSmaller(treeMap, i, j)
                || allAboveAreSmaller(treeMap, i, j)
                || allBelowAreSmaller(treeMap, i, j)
            ) {
                result.add(Pair(i, j))
            }
        }
    }

    return result.size
}

private fun part02(treeMap: Array<Array<Int>>) = treeMap.indices.maxOf { rowIndex ->
    treeMap[rowIndex].indices.maxOf { columnIndex ->
        viewLengthOnLeft(treeMap, rowIndex, columnIndex) *
                viewLengthOnRight(treeMap, rowIndex, columnIndex) *
                viewLengthAbove(treeMap, rowIndex, columnIndex) *
                viewLengthBelow(treeMap, rowIndex, columnIndex)
    }
}

private fun isEdgeTree(treeMap: Array<Array<Int>>, i: Int, j: Int) =
    i == 0 || j == 0 || i == treeMap.size - 1 || j == treeMap[i].size - 1

private fun allOnTheLeftAreSmaller(treeMap: Array<Array<Int>>, i: Int, j: Int) =
    (0 until j).all { treeMap[i][it] < treeMap[i][j] }

private fun allOnTheRightAreSmaller(treeMap: Array<Array<Int>>, i: Int, j: Int) =
    (j + 1 until treeMap[i].size).all { treeMap[i][it] < treeMap[i][j] }

private fun allAboveAreSmaller(treeMap: Array<Array<Int>>, i: Int, j: Int) =
    (0 until i).all { treeMap[it][j] < treeMap[i][j] }

private fun allBelowAreSmaller(treeMap: Array<Array<Int>>, i: Int, j: Int) =
    (i + 1 until treeMap.size).all { treeMap[it][j] < treeMap[i][j] }

private fun viewLengthOnLeft(treeMap: Array<Array<Int>>, i: Int, j: Int): Int {
    var length = 0
    (j - 1 downTo  0).firstOrNull {
        length++
        treeMap[i][it] >= treeMap[i][j]
    }
    return length
}

private fun viewLengthOnRight(treeMap: Array<Array<Int>>, i: Int, j: Int): Int {
    var length = 0
    (j + 1 until treeMap[i].size).firstOrNull {
        length++
        treeMap[i][it] >= treeMap[i][j]
    }
    return length
}

private fun viewLengthAbove(treeMap: Array<Array<Int>>, i: Int, j: Int): Int {
    var length = 0
    (i - 1 downTo 0).firstOrNull {
        length++
        treeMap[it][j] >= treeMap[i][j]
    }
    return length
}

private fun viewLengthBelow(treeMap: Array<Array<Int>>, i: Int, j: Int): Int {
    var length = 0
    (i + 1 until treeMap.size).firstOrNull {
        length++
        treeMap[it][j] >= treeMap[i][j]
    }
    return length
}
