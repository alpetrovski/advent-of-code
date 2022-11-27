package day05

import java.lang.Integer.max
import java.nio.file.Paths
import kotlin.math.abs

fun main() {
    val lines = Paths.get("src/day05/input.in").toFile().readLines().map { Line(it) }

    val resultPart1 = lines
        .filter { it.isHorizontal() || it.isVertical() }
        .map { it.getLinePoints() }
        .flatMap { it.asIterable() }
        .groupBy { it }
        .count { it.value.count() >= 2 }

    val resultPart2 = lines
        .filter { it.isHorizontal() || it.isVertical() || it.isDiagonal() }
        .map { it.getLinePoints() }
        .flatMap { it.asIterable() }
        .groupBy { it }
        .count { it.value.count() >= 2 }

    println("RESULT part 01: $resultPart1")
    println("RESULT part 02: $resultPart2")
}

class Line(input: String) {

    private val lineStartX: Int
    private val lineStartY: Int
    private val lineEndX: Int
    private val lineEndY: Int

    init {
        val lineEnds = input.split(" -> ")
        lineStartX = lineEnds[0].split(",")[0].toInt()
        lineStartY = lineEnds[0].split(",")[1].toInt()
        lineEndX = lineEnds[1].split(",")[0].toInt()
        lineEndY = lineEnds[1].split(",")[1].toInt()
    }

    fun isVertical() = lineStartX == lineEndX
    fun isHorizontal() = lineStartY == lineEndY
    fun isDiagonal() = abs(lineStartX - lineEndX) == abs(lineStartY - lineEndY)

    fun getLinePoints(): List<Int> = (0..max(abs(lineStartX - lineEndX), abs(lineStartY - lineEndY))).map {
        val x =
            if (lineStartX < lineEndX) lineStartX + it else if (lineStartX > lineEndX) lineStartX - it else lineStartX
        val y =
            if (lineStartY < lineEndY) lineStartY + it else if (lineStartY > lineEndY) lineStartY - it else lineStartY
        1000 * x + y
    }
}