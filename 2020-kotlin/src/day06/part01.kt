package day06

import java.nio.file.Paths

fun main() {
    var result = 0

    Paths.get("src/day06/input.in").toFile().readLines().map { it.toSet() }.reduce { previous, current ->
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

