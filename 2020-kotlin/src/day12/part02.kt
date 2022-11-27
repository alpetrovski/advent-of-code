package day12

import java.nio.file.Paths
import kotlin.math.abs

var waypointX = 10
var waypointY = 1

fun main() {
    var shipX = 0
    var shipY = 0

    Paths.get("src/day12/input.in").toFile().readLines().forEach { instruction ->
        val command = instruction[0]
        val value = instruction.substring(1).toInt()

        when(command) {
            'F' -> {
                shipX += value * waypointX
                shipY += value * waypointY
            }
            'E' -> waypointX += value
            'W' -> waypointX -= value
            'N' -> waypointY += value
            'S' -> waypointY -= value
            'R' -> {
               rotateWaypoint((value / 90) % 4)
             }
            'L' -> {
                rotateWaypoint(4 - ((value / 90) % 4))
            }
        }
    }

    println(abs(shipX) + abs(shipY))
}

private fun rotateWaypoint(times: Int) {
    for (i in 1 .. times) {
        if (waypointX == 0) {
            waypointX = waypointY
            waypointY = 0
        } else if ( waypointY == 0) {
            waypointY = waypointX * -1
            waypointX = 0
        } else if (waypointX > 0 && waypointY > 0) {
            val oldX = waypointX
            waypointX = waypointY
            waypointY = -1 * oldX
        } else if (waypointX > 0 && waypointY < 0) {
            val oldX = waypointX
            waypointX = waypointY
            waypointY = -1 * oldX
        } else if (waypointX < 0 && waypointY < 0) {
            val oldX = waypointX
            waypointX = waypointY
            waypointY = -1 * oldX
        } else if (waypointX < 0 && waypointY > 0) {
            val oldX = waypointX
            waypointX = waypointY
            waypointY = -1 * oldX
        }
    }
}