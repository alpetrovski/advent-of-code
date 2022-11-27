package day17

import java.nio.file.Paths
import kotlin.math.max

fun main() {
    val input = Paths.get("src/day17/input.in").toFile().readLines()[0]
    val match = Regex("x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)").find(input)!!

    val (xMin, xMax, yMin, yMax) = match.destructured
    val targetArea = TargetArea(xMin.toInt(), xMax.toInt(), yMin.toInt(), yMax.toInt())

    println("Result Part 1: ${maxHeight(xMax.toInt(), yMin.toInt(), targetArea)}")
    println("Result Part 2: ${possibleCombinations(xMax.toInt(), yMin.toInt(), targetArea)}")
}

fun possibleCombinations(xMax: Int, yMin: Int, targetArea: TargetArea): Int {
    return (1..xMax).sumOf { x ->
        (yMin..xMax).count { y -> throwProbe(x, y, targetArea).success }
    }
}

fun maxHeight(xMax: Int, yMin: Int, targetArea: TargetArea): Int {
    return (1..xMax).map { x ->
        (yMin..xMax)
            .map { y -> throwProbe(x, y, targetArea) }
            .filter { it.success }
            .map { it.maxHeight }
    }.flatMap { it.asIterable() }.maxOrNull()!!
}

fun throwProbe(initialVeloX: Int, initialVeloY: Int, targetArea: TargetArea): ProbeResult {
    var veloX = initialVeloX
    var veloY = initialVeloY

    var posX = 0
    var posY = 0
    var maxHeight = 0

    while (posY >= targetArea.yMin) {
        posX += veloX
        posY += veloY

        maxHeight = max(maxHeight, posY)

        veloX = max(veloX - 1,0)
        veloY--

        if (targetArea.contains(posX, posY)) {
            return ProbeResult(true, maxHeight)
        }
    }
    return ProbeResult(false)
}

data class ProbeResult(
    val success: Boolean,
    val maxHeight: Int = -1
)

data class TargetArea(
    val xMin: Int,
    val xMax: Int,
    val yMin: Int,
    val yMax: Int
) {
    fun contains(x: Int, y: Int) = x in xMin..xMax && y in yMin..yMax
}