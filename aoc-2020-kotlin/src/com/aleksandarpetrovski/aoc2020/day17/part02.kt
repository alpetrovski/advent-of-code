package com.aleksandarpetrovski.aoc2020.day17

import java.nio.file.Paths
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = Paths.get("2020-kotlin/src/day17/input.in").toFile().readLines()
    val d1size = lines.size + 12
    val d2size = lines[0].length + 12
    val d3size = 13
    val d4size = 13

    var cube = Array(d1size) { Array(d2size) { Array(d3size) { CharArray(d4size) } } }

    // initial state
    for (i1 in 0 until d1size) {
        for (i2 in 0 until d2size) {
            for (i3 in 0 until d3size) {
                for (i4 in 0 until d4size) {
                    if (i1 >= 6 && i1 < (d1size - 6) && i2 >= 6 && i2 < (d2size - 6) && i3 == 6 && i4 == 6) {
                        cube[i1][i2][i3][i4] = lines[i1 - 6][i2 - 6]
                    } else {
                        cube[i1][i2][i3][i4] = '.'
                    }
                }
            }
        }
    }

    // 6 cycles, each cycle calculate new cube
    for (cycle in 1 .. 6) {
        val nextCube = Array(d1size) { Array(d2size) { Array(d3size) { CharArray(d4size) } } }
        for (i1 in 0 until d1size) {
            for (i2 in 0 until d2size) {
                for (i3 in 0 until d3size) {
                    for (i4 in 0 until d4size) {
                        val activeNeighbors = calculateActive(i1, i2, i3, i4, cube)
                        if (cube[i1][i2][i3][i4] == '#') {
                            if (activeNeighbors == 2 || activeNeighbors == 3) {
                                nextCube[i1][i2][i3][i4] = '#'
                            } else {
                                nextCube[i1][i2][i3][i4] = '.'
                            }
                        } else {
                            if (activeNeighbors == 3) {
                                nextCube[i1][i2][i3][i4] = '#'
                            } else {
                                nextCube[i1][i2][i3][i4] = '.'
                            }
                        }
                    }
                }
            }
        }
        cube = nextCube
    }

    var count = 0
    for (i1 in 0 until d1size) {
        for (i2 in 0 until d2size) {
            for (i3 in 0 until d3size) {
                for (i4 in 0 until d4size) {
                    if (cube[i1][i2][i3][i4] == '#') {
                        count++
                    }
                }
            }
        }
    }

    println("Result: $count")
}

fun calculateActive(p1: Int, p2: Int, p3: Int, p4: Int, cube: Array<Array<Array<CharArray>>>): Int {
    var count = 0
    for (i1 in max(p1 - 1, 0) .. min(p1 + 1, cube.size - 1)) {
        for (i2 in max(p2 - 1, 0) .. min(p2 + 1, cube[p1].size - 1)) {
            for (i3 in max(p3 - 1, 0) .. min(p3 + 1, cube[p1][p2].size - 1)) {
                for (i4 in max(p4 - 1, 0)..min(p4 + 1, cube[p1][p2][p3].size - 1)) {
                    if (cube[i1][i2][i3][i4] == '#' && (i1 != p1 || i2 != p2 || i3 != p3 || i4 != p4)) {
                        count++
                    }
                }
            }
        }
    }
    return count
}
