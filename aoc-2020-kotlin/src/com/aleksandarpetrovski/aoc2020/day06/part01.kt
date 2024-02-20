package com.aleksandarpetrovski.aoc2020.day06

import java.nio.file.Paths

fun main() {
    var result = 0

    Paths.get("2020-kotlin/src/day06/input.in").toFile().readLines().map { it.toSet() }.reduce { previous, current ->
        if (current.isEmpty()) {
            result += previous.size
            emptySet()
        } else {
            current.union(previous)
        }
    }.also {
        result += it.size
    }

    println(result)
}

