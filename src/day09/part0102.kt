package day09

import java.nio.file.Paths

fun main() {
    val caveMatrix = Paths.get("src/day09/input.in").toFile().readLines().map { line ->
        line.toCharArray().map { it.toString().toInt() }.toIntArray()
    }.toTypedArray()

    val lowPoints = caveMatrix.indices.flatMap { i ->
        caveMatrix[i].indices.filter { j ->
            (i == 0 || caveMatrix[i][j] < caveMatrix[i - 1][j])
                    && (i == caveMatrix.size - 1 || caveMatrix[i][j] < caveMatrix[i + 1][j])
                    && (j == 0 || caveMatrix[i][j] < caveMatrix[i][j - 1])
                    && (j == caveMatrix[i].size - 1 || caveMatrix[i][j] < caveMatrix[i][j + 1])
        }.map { j -> Pair(i, j) }.asIterable()
    }

    val resultPart1 = lowPoints.sumOf { caveMatrix[it.first][it.second] + 1 }

    val resultPart2 = lowPoints
        .asSequence()
        .map { Pair(it, getHigherNeighbours(caveMatrix, it.first, it.second).distinct().count()) }
        .sortedByDescending { it.second }
        .take(3)
        .map { it.second }
        .reduce { acc, next -> acc * next }

    println("RESULT PART 1: $resultPart1")
    println("RESULT PART 2: $resultPart2")
}

fun getHigherNeighbours(caveMatrix: Array<IntArray>, x: Int, y: Int): List<Pair<Int, Int>> =
    listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
        .filter { isHigherNeighbour(caveMatrix, x, y, it.first, it.second) }
        .map { getHigherNeighbours(caveMatrix, it.first, it.second) }
        .flatMap { it.asIterable() } + Pair(x, y)


fun isHigherNeighbour(caveMatrix: Array<IntArray>, x: Int, y: Int, x2: Int, y2: Int) =
    x2 >= 0 && x2 < caveMatrix.size
            && y2 >= 0 && y2 < caveMatrix[0].size
            && caveMatrix[x][y] < caveMatrix[x2][y2]
            && caveMatrix[x2][y2] != 9