package day13

import java.nio.file.Paths

fun main() {
    val input = Paths.get("2021-kotlin/src/day13/input.in").toFile().readLines()

    var points = input.filter { it.contains(",") }.map {
        it.split(",").let { split -> Point(split[0].toInt(), split[1].toInt()) }
    }

    val foldingInstructions = input.filter { it.contains("fold along") }.map {
        it.substringAfter("fold along ").split("=").let { split -> FoldingInstruction(split[0][0], split[1].toInt()) }
    }

    part1(points, foldingInstructions[0])
    part2(points, foldingInstructions)
}

fun part1(points: List<Point>, foldingInstruction: FoldingInstruction) {
    val resultPart1 = foldPaper(points, foldingInstruction).count()
    println("Result Part 1: $resultPart1")
}

fun part2(points: List<Point>, foldingInstructions: List<FoldingInstruction>) {
    var foldedPoints = points
    foldingInstructions.forEach { foldingInstruction ->
        foldedPoints = foldPaper(foldedPoints, foldingInstruction)
    }

    println("Result Part 2:")
    (0..foldedPoints.maxOf { point -> point.y }).forEach { y ->
        (0..foldedPoints.maxOf { point -> point.x }).forEach { x ->
            if (foldedPoints.contains(Point(x, y))) {
                print(" @ ")
            } else {
                print("   ")
            }
        }
        println()
    }
}

fun foldPaper(points: List<Point>, foldingInstruction: FoldingInstruction): List<Point> =
    points.map { point ->
        if (foldingInstruction.axis == 'x') {
            Point(fold(point.x, foldingInstruction.length), point.y)
        } else {
            Point(point.x, fold(point.y, foldingInstruction.length))
        }
    }.distinct()

fun fold(number: Int, foldPosition: Int) = if (number > foldPosition) {
    2 * foldPosition - number
} else {
    number
}

data class Point(val x: Int, val y: Int)
data class FoldingInstruction(val axis: Char, val length: Int)
