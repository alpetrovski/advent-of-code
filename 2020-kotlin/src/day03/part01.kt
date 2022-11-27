package day03

import java.nio.file.Paths

fun main() {
    val track = Paths.get("src/day03/input.in").toFile().readLines()
    val result = countTrees(1, 3, track)
    println(result)
}

private fun countTrees(stepDown: Int, stepRight: Int, track: List<String>): Long {
    val trackWidth = track[0].length
    return IntProgression.fromClosedRange(stepDown, track.size - 1, stepDown).count {lineNr ->
        val positionForLine = (stepRight * (lineNr / stepDown)) % trackWidth
        track[lineNr][positionForLine] == '#'
    }.toLong()
}