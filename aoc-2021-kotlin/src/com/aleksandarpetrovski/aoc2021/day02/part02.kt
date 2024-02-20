package com.aleksandarpetrovski.aoc2021.day02

import java.nio.file.Paths

fun main() {
    var forward = 0L
    var aim = 0L
    var depth = 0L

    Paths.get("2021-kotlin/src/day02/input.in").toFile().readLines().map {
        val lineComponents = it.split(" ")
        Pair(lineComponents[0], lineComponents[1].toLong())
    }.forEach {
        when (it.first) {
            "forward" -> {
                forward += it.second
                depth += aim * it.second
            }
            "up" -> aim -= it.second
            "down" -> aim += it.second
        }
    }

    println("RESULT: ${depth * forward}")
}
